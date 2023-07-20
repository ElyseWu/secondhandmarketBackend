package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public record UserEntity(
        @Id String username,
        String password,
        String location,
        boolean enabled
) {
}
