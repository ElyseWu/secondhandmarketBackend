package team3.secondhand.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("items_image")
public record ItemImageEntity (
        @Id
        String url,
        Long itemId
) {
}


