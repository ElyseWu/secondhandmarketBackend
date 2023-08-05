package team3.secondhand.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import team3.secondhand.entity.DescriptionEntity;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.ItemImageEntity;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.model.ItemDto;
import team3.secondhand.repository.DescriptionRepository;
import team3.secondhand.repository.ItemImageRepository;
import team3.secondhand.repository.ItemRepository;
import team3.secondhand.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private final DescriptionRepository descriptionRepository;

    private final ItemRepository itemRepository;

    private final ItemImageRepository itemImageRepository;
    private final UserRepository userRepository;

    public SearchService(DescriptionRepository descriptionRepository, ItemRepository itemRepository, ItemImageRepository itemImageRepository,
                         UserRepository userRepository) {
        this.descriptionRepository = descriptionRepository;
        this.itemImageRepository = itemImageRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @Cacheable("items")
    public List<ItemDto> searchByKeyword(String keyword) {
        // step1: using passed keyword to search corresponding description entity
        List<DescriptionEntity> descriptionEntities = descriptionRepository.findByDescriptionContaining(keyword);
        if (descriptionEntities == null) {
            return new ArrayList<>();
        }
        // step2: using description entities' id to denote searched item id
        List<Long> itemIds = new ArrayList<>();
        for(DescriptionEntity descriptionEntity : descriptionEntities) {
            itemIds.add(descriptionEntity.id());
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

//            ItemEntity itemEntity = itemRepository.getItemEntityByIdAndIsSold(id, false);
            // 2. get ItemImageEntities
            List<ItemImageEntity> itemImageEntities = itemImageRepository.getItemImageEntitiesByItemId(id);

            // 3. extract URL from image entities
            List<String> itemImageUrls = new ArrayList<>();
            for(ItemImageEntity itemImageEntity : itemImageEntities) {
                itemImageUrls.add(itemImageEntity.url());
            }
            ItemDto itemDto = new ItemDto(itemEntity, itemImageUrls);
            items.add(itemDto);
        }

        items.sort((one, two) -> (int) (one.itemId() - two.itemId()));
        return items;
    }

}
