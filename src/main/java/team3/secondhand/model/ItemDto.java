package team3.secondhand.model;

import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.ItemImageEntity;

import java.time.LocalDate;
import java.util.List;

public record ItemDto(
        Long itemId,
        String itemName,
        Double itemPrice,
        String itemDescription,
        String itemCondition,
        LocalDate itemPostedDay,
        String itemCategory,
        Boolean itemOnSale,
        List<String> itemImageUrls
) {
    public ItemDto(ItemEntity itemEntity, List<String> itemImageUrls) {
        this(itemEntity.getId(),
                itemEntity.getName(),
                itemEntity.getPrice(),
                itemEntity.getDescription(),
                itemEntity.getCondition(),
                itemEntity.getPostedDay(),
                itemEntity.getCategory(),
                itemEntity.getOnSale(),
                itemImageUrls);
    }
}
