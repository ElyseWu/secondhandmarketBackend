package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("authority")
public record AuthorityEntity(
    @Id
    String username,
    String authority
) {
}
