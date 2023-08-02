package team3.secondhand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("chats")
public record ChatEntity(
   @Id
   Long id,
   String username1,
   String username2
) {
}

