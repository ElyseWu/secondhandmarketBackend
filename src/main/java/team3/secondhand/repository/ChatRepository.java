package team3.secondhand.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.ChatEntity;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<ChatEntity, Long> {
    @Modifying
    @Query("INSERT INTO chats VALUES(:username1, :username2)")
    void insert(String username1, String username2);

    @Query("SELECT * FROM chats WHERE username1 = :username OR username2 = :username")
    List<ChatEntity> findChatEntitiesByUsername1OrUsername2(String username);

    ChatEntity getChatEntityById(Long chatId);
}
