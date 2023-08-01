package team3.secondhand.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("messages")
public record MessageEntity(
   @Id
   Long id,
   String content,
   String senderName,
   String receiverName,
   LocalDateTime sendTime,
   Long chatId
) {
}
