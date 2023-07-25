package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("items")
public record ItemEntity(
        @Id Long id,
        String username,
        String name,
        Double price,
        String description,
        String condition,
        LocalDate postedDay,
//        String category,
        String category,
        Boolean isSold
) {
}
