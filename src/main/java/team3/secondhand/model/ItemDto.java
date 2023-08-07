package team3.secondhand.model;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.Location;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record ItemDto(
        Long itemId,
        String userName,
        String itemName,
        Double itemPrice,
        String itemDescription,
        String itemCondition,
        LocalDate itemPostedDay,
        String itemCategory,
        Boolean itemIsSold,
        List<String> itemImageUrls,
        Double lat,
        Double lon
) implements Serializable {
    public ItemDto(ItemEntity itemEntity, List<String> itemImageUrls, Double lat, Double lon) {
        this(itemEntity.id(),
                itemEntity.username(),
                itemEntity.name(),
                itemEntity.price(),
                itemEntity.description(),
                itemEntity.condition(),
                itemEntity.postedDay(),
                itemEntity.category(),
                itemEntity.isSold(),
                itemImageUrls,
                lat, lon);
    }
}
