package team3.secondhand.model;

import team3.secondhand.entity.ItemEntity;

import java.io.Serializable;
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
        Boolean itemIsSold,
        List<String> itemImageUrls
) implements Serializable {
    public ItemDto(ItemEntity itemEntity, List<String> itemImageUrls) {
        this(itemEntity.id(),
                itemEntity.name(),
                itemEntity.price(),
                itemEntity.description(),
                itemEntity.condition(),
                itemEntity.postedDay(),
                itemEntity.category(),
                itemEntity.isSold(),
                itemImageUrls);
    }
}
