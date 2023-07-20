package team3.secondhand.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.ItemImageEntity;
import team3.secondhand.model.ItemDto;
import team3.secondhand.repository.ItemImageRepository;
import team3.secondhand.repository.ItemRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    private final ItemImageRepository itemImageRepository;

    private final ItemImageStorageService itemImageStorageService;

    public ItemService(ItemRepository itemRepository, ItemImageRepository itemImageRepository, ItemImageStorageService itemImageStorageService) {
        this.itemRepository = itemRepository;
        this.itemImageRepository = itemImageRepository;
        this.itemImageStorageService = itemImageStorageService;
    }

    // TODO: Add Cache features to fast-loading
    // TODO: Consider using Redis as our candidate solution
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

    // When calling this API, we should also use ItemImageStorageService to store image in CCS
    // ItemImageStorageService will store images provided by clients in GCS and generate a list of URLs for these images
    // we can use generated URLs to store in corresponding database table
    @Transactional
    public void upload(ItemEntity item, MultipartFile[] images) {
        // update item repository
        item = itemRepository.save(item);

        // update item image repository
        // When we use ItemImageStorage service generate a list of URLs
        // we can use for loop to generate it to item_image table
        List<String> mediaLinks = Arrays.stream(images).parallel().map(image -> itemImageStorageService.save(image)).collect(Collectors.toList());
        for (String mediaLink : mediaLinks) {
            itemImageRepository.insert(mediaLink, item.id());
        }

        // WEIRD BUG
        // ItemImageEntity itemImageEntity = new ItemImageEntity("https://fakeurl.com", item.getId());
        // ItemImageRepository.save(itemImageEntity);
    }

    @Transactional
    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Transactional
    public void modifyItem(Long itemId, String name, Double price, String description, String condition, String category, Boolean onSale, MultipartFile[] images) {
        ItemEntity oldItem = itemRepository.getItemEntityById(itemId);
        ItemEntity newItem = new ItemEntity(oldItem.id(),
                name,
                price,
                description,
                condition,
                oldItem.postedDay(),
                category,
                onSale);
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
}
