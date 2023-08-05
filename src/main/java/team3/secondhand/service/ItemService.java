package team3.secondhand.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team3.secondhand.entity.*;
import team3.secondhand.model.ItemDto;
import team3.secondhand.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    private final ItemImageRepository itemImageRepository;

    private final ItemImageStorageService itemImageStorageService;

    private final DescriptionRepository descriptionRepository;
    private final LocationRepository locationRepository;

    public ItemService(ItemRepository itemRepository, ItemImageRepository itemImageRepository,
                       ItemImageStorageService itemImageStorageService, DescriptionRepository descriptionRepository,
                       LocationRepository locationRepository) {
        this.itemRepository = itemRepository;
        this.itemImageRepository = itemImageRepository;
        this.itemImageStorageService = itemImageStorageService;
        this.locationRepository = locationRepository;
        this.descriptionRepository = descriptionRepository;
    }

//    @Cacheable(cacheNames = "items", key = "#itemId")
    public ItemDto getItem(Long itemId) {
        // 1. get ItemEntity
        ItemEntity itemEntity = itemRepository.getItemEntityById(itemId);

        // 2. get ItemImageEntities
        List<ItemImageEntity> itemImageEntities = itemImageRepository.getItemImageEntitiesByItemId(itemId);

        // 3. extract URL from image entities
        List<String> itemImageUrls = new ArrayList<>();
        for(ItemImageEntity itemImageEntity : itemImageEntities) {
            itemImageUrls.add(itemImageEntity.url());
        }

        // 4. construct ItemDto and return
        return new ItemDto(itemEntity, itemImageUrls);
    }

//    @Cacheable("items")
    public List<ItemDto> getMyItems(String username) {
        //1. find all itemEntities by username
        //2. iterate itemEntities, find itemImageEntities by itemId
        List<ItemEntity> itemEntities = itemRepository.getAllByUsername(username);
        List<ItemDto> myItems = new ArrayList<>();
        for(ItemEntity item: itemEntities) {
            List<ItemImageEntity> itemImageEntities = itemImageRepository.getItemImageEntitiesByItemId(item.id());
            List<String> itemImageUrls = new ArrayList<>();
            for (ItemImageEntity itemImageEntity : itemImageEntities) {
                itemImageUrls.add(itemImageEntity.url());
            }
            myItems.add(new ItemDto(item, itemImageUrls));
        }
        myItems.sort((one, two) -> (int) (one.itemId() - two.itemId()));

        return myItems;
    }

//    @Cacheable("items")
    public List<ItemDto> getAllItems(double lat, double lon, String distance) {
        // iterator of all items
        Set<Long> itemIdsByDistance = locationRepository.searchByDistance(lat, lon, distance);
        List<ItemDto> items = new ArrayList<>();
        for (Long id: itemIdsByDistance) {
            ItemEntity item = itemRepository.getItemEntityById(id);
            if (item.isSold()) {
                continue;
            }
            List<ItemImageEntity> itemImageEntities = itemImageRepository.getItemImageEntitiesByItemId(item.id());
            List<String> itemImageUrls = new ArrayList<>();
            // extract image urls
            for (ItemImageEntity itemImageEntity : itemImageEntities) {
                itemImageUrls.add(itemImageEntity.url());
            }
            items.add(new ItemDto(item, itemImageUrls));
        }
//        Iterator<ItemEntity> iterator = itemRepository.findAllByIsSold(false).iterator();
//        List<ItemDto> items = new ArrayList<>();
//        while (iterator.hasNext()) {
//            ItemEntity item = iterator.next();
//            // get item images
//            List<ItemImageEntity> itemImageEntities = itemImageRepository.getItemImageEntitiesByItemId(item.id());
//            List<String> itemImageUrls = new ArrayList<>();
//            // extract image urls
//            for (ItemImageEntity itemImageEntity : itemImageEntities) {
//                itemImageUrls.add(itemImageEntity.url());
//            }
//            items.add(new ItemDto(item, itemImageUrls));
//        }
        items.sort((one, two) -> (int) (one.itemId() - two.itemId()));
        return items;
    }

    // When calling this API, we should also use ItemImageStorageService to store image in CCS
    // ItemImageStorageService will store images provided by clients in GCS and generate a list of URLs for these images
    // we can use generated URLs to store in corresponding database table

    // Meanwhile, because we need consider keywords search
    // when we upload, we should save this item's DescriptionEntity in ElasticSearchRepository

//    @CacheEvict(cacheNames = "items")
    @Transactional
    public void upload(ItemEntity item, MultipartFile[] images, double lat, double lon) {
        // update item repository
        item = itemRepository.save(item);

        // update item image repository
        // When we use ItemImageStorage service generate a list of URLs
        // we can use for loop to generate it to item_image table
        List<String> mediaLinks = Arrays.stream(images).parallel().map(image -> itemImageStorageService.save(image)).collect(Collectors.toList());
        for (String mediaLink : mediaLinks) {
            itemImageRepository.insert(mediaLink, item.id());
        }

        // construct and save corresponding DescriptionEntity in ElasticSearchRepository
        DescriptionEntity descriptionEntity = new DescriptionEntity(item.id(), item.description());
        descriptionRepository.save(descriptionEntity);

        //save location
        Location location = new Location(item.id(), new GeoPoint(lat, lon));
        locationRepository.save(location);

    }

//    @CacheEvict(cacheNames = "items", key = "#itemId")
    @Transactional
    public void deleteItem(Long itemId) {

        itemRepository.deleteById(itemId);
    }

//    @CacheEvict(cacheNames = "items", key = "#itemId")
    @Transactional
    public void modifyItem(Long itemId, String username, String name, Double price, String description, String condition, String category, MultipartFile[] images) {
        ItemEntity oldItem = itemRepository.getItemEntityById(itemId);
        ItemEntity newItem = new ItemEntity(oldItem.id(),
                username,
                name,
                price,
                description,
                condition,
                oldItem.postedDay(),
                category,
                oldItem.isSold());
        itemRepository.save(newItem);

        List<String> mediaLinks = Arrays.stream(images).parallel().map(image -> itemImageStorageService.save(image)).collect(Collectors.toList());
        //1. delete origin link in item_image
        //2. insert new link in item_image

        List<ItemImageEntity> itemUrls = itemImageRepository.getItemImageEntitiesByItemId(itemId);
        for (ItemImageEntity itemImage: itemUrls) {
            itemImageRepository.delete(itemImage);
        }

        for (String mediaLink : mediaLinks) {
            itemImageRepository.insert(mediaLink, itemId);
        }
    }

//    @CacheEvict(cacheNames = "items", key = "#itemId")
    public void markItemSoldOrRelist(Long itemId) {
        //1. get ItemEntity by id
        //2. renew this item
        ItemEntity item = itemRepository.getItemEntityById(itemId);
        ItemEntity newItem = new ItemEntity(item.id(),
                item.username(),
                item.name(),
                item.price(),
                item.description(),
                item.condition(),
                item.postedDay(),
                item.category(),
                !item.isSold());
        itemRepository.save(newItem);
    }
}
