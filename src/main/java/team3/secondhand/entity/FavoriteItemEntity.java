package team3.secondhand.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("favorite_items")
public record FavoriteItemEntity(
        @Id Long id,
        String username,
        Long itemId
) {
}
