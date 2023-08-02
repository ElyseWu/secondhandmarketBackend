package team3.secondhand.service;


import org.springframework.stereotype.Service;
import team3.secondhand.entity.FavoriteItemEntity;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.ItemImageEntity;
import team3.secondhand.model.ItemDto;
import team3.secondhand.repository.FavoriteItemRepository;
import team3.secondhand.repository.ItemImageRepository;
import team3.secondhand.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteItemService {
    private final FavoriteItemRepository favoriteItemRepository;

    private final ItemRepository itemRepository;

    private final ItemImageRepository itemImageRepository;

    public FavoriteItemService(FavoriteItemRepository favoriteItemRepository, ItemRepository itemRepository, ItemImageRepository itemImageRepository) {
        this.favoriteItemRepository = favoriteItemRepository;
        this.itemRepository = itemRepository;
        this.itemImageRepository = itemImageRepository;
    }


    public List<ItemDto> getMyFavoriteItems(String username) {
        List<ItemDto> myFavorites = new ArrayList<>();
        // step1: get all favorite items entities from this user
        List<FavoriteItemEntity> favoriteItemEntities = favoriteItemRepository.getFavoriteItemEntitiesByUsername(username);

        // build itemDto from favorite item entity
        for(FavoriteItemEntity favoriteItemEntity : favoriteItemEntities) {
            ItemEntity itemEntity = itemRepository.getItemEntityById(favoriteItemEntity.itemId());
            List<ItemImageEntity> itemImageEntities = itemImageRepository.getItemImageEntitiesByItemId(favoriteItemEntity.itemId());
            List<String> itemImageUrls = new ArrayList<>();
            for(ItemImageEntity itemImageEntity : itemImageEntities) {
                itemImageUrls.add(itemImageEntity.url());
            }
            ItemDto itemDto = new ItemDto(itemEntity, itemImageUrls);
            myFavorites.add(itemDto);
        }
        return myFavorites;
    }


    public void markAsFavorite(Long itemId, String username) {
        favoriteItemRepository.insert(itemId, username);
    }

    public void unmarkFavorite(String username, Long itemId) {
        favoriteItemRepository.deleteByItemIdAndUsername(itemId, username);
    }



}
