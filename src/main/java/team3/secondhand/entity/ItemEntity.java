package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Table("items")
public record ItemEntity(
        @Id
        Long id,
        String name,
        Double price,
        String description,
        String condition,
        Date postedDay,
        Boolean onSale
) {
}
