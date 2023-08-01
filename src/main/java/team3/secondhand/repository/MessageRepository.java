package team3.secondhand.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.MessageEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
    @Modifying
    @Query("INSERT INTO messages(content, sender_name, receiver_name, send_time, chat_id) VALUES(:content, :senderName, :receiverName, :sendTime, :chatId)")
    void insert(String content, String senderName, String receiverName, LocalDateTime sendTime, Long chatId);

    List<MessageEntity> getMessageEntitiesByChatIdOrderBySendTimeAsc(Long chatId);
}
