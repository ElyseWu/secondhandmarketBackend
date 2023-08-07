package team3.secondhand.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import team3.secondhand.entity.*;
import team3.secondhand.model.ItemDto;
import team3.secondhand.repository.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {
    private final DescriptionRepository descriptionRepository;

    private final ItemRepository itemRepository;

    private final ItemImageRepository itemImageRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public SearchService(DescriptionRepository descriptionRepository, ItemRepository itemRepository, ItemImageRepository itemImageRepository,
                         UserRepository userRepository, LocationRepository locationRepository) {
        this.descriptionRepository = descriptionRepository;
        this.itemImageRepository = itemImageRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Cacheable("items")
    public List<ItemDto> searchByKeyword(String keyword, double lat, double lon, String distance) {
        //1. get item ids by distance
        //2. get descriotionEntities by keyword and get ids from descriotionEntities
        //3. get common ids from idbydistance and idbykeyword
        Set<Long> itemIdsByDistance = locationRepository.searchByDistance(lat, lon, distance);
        // step1: using passed keyword to search corresponding description entity
        List<DescriptionEntity> descriptionEntities = descriptionRepository.findByDescriptionContaining(keyword);
        if (descriptionEntities == null || itemIdsByDistance == null || itemIdsByDistance.isEmpty()) {
            return new ArrayList<>();
        }
        // step2: using description entities' id to denote searched item id
        List<Long> itemIds = new ArrayList<>();
        for(DescriptionEntity descriptionEntity : descriptionEntities) {
            if (itemIdsByDistance.contains(descriptionEntity.id())) {
                itemIds.add(descriptionEntity.id());
            }
        }

        // if we can not find any matched descriptions items
        // return empty array list
        if(itemIds.isEmpty()) {
            return new ArrayList<>();
        }

        // step3: using itemIds to construct itemDtos
        // first get ItemEntity and then get ItemImageEntity
        // finally construct itemDtos -> return
        List<ItemDto> items = new ArrayList<>();
        for(Long id : itemIds) {
            // 1. get ItemEntity
            ItemEntity itemEntity = itemRepository.getItemEntityById(id);
            if (itemEntity.isSold()) {
                continue;
            }
            // 2. get ItemImageEntities
            List<ItemImageEntity> itemImageEntities = itemImageRepository.getItemImageEntitiesByItemId(id);

            // 3. extract URL from image entities
            List<String> itemImageUrls = new ArrayList<>();
            for(ItemImageEntity itemImageEntity : itemImageEntities) {
                itemImageUrls.add(itemImageEntity.url());
            }
            Location location = locationRepository.getLocationById(id);
            ItemDto itemDto = new ItemDto(itemEntity, itemImageUrls, location.geoPoint().getLat(), location.geoPoint().getLon());
            items.add(itemDto);
        }

        items.sort((one, two) -> (int) (one.itemId() - two.itemId()));
        return items;
    }

    @Cacheable("items")
    public List<ItemDto> searchByCategory(String category, double lat, double lon, String distance) {
        Set<Long> itemIdsByDistance = locationRepository.searchByDistance(lat, lon, distance);
        List<ItemEntity> itemEntities = itemRepository.findAllByCategoryAndIsSold(category, false);
        if (itemEntities == null || itemIdsByDistance == null || itemIdsByDistance.isEmpty()) {
            return new ArrayList<>();
        }

        List<ItemDto> items = new ArrayList<>();
        for (ItemEntity item: itemEntities) {
            if (!itemIdsByDistance.contains(item.id())) {
                continue;
            }
            List<ItemImageEntity> itemImageEntities = itemImageRepository.getItemImageEntitiesByItemId(item.id());
            List<String> itemUrls = new ArrayList<>();
            for (ItemImageEntity itemImage : itemImageEntities) {
                itemUrls.add(itemImage.url());
            }
            Location location = locationRepository.getLocationById(item.id());
            items.add(new ItemDto(item, itemUrls, location.geoPoint().getLat(), location.geoPoint().getLon()));
        }
        items.sort((one, two) -> (int) (one.itemId() - two.itemId()));

        return items;
    }

}
