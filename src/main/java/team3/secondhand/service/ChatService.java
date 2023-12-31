package team3.secondhand.service;

import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import team3.secondhand.entity.ChatEntity;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.MessageEntity;
import team3.secondhand.repository.ChatRepository;
import team3.secondhand.repository.ItemRepository;
import team3.secondhand.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    private final MessageRepository messageRepository;

    private final ItemRepository itemRepository;

    private static final String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
    public ChatService(ChatRepository chatRepository, MessageRepository messageRepository, ItemRepository itemRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.itemRepository = itemRepository;
    }

    public void askForSeller(Long itemId, String senderName, String content) {
        // get current time and format it
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        LocalDateTime localDateTime = LocalDateTime.now();

        // step1: create a chat between sender(buyer) and receiver(seller)
        ItemEntity itemEntity = itemRepository.getItemEntityById(itemId);
        String receiverName = itemEntity.username();
        String itemName= itemEntity.name();
        ChatEntity chatEntity = chatRepository.save(new ChatEntity(null, senderName, receiverName));
        Long chatId = chatEntity.id();

        // step2: send a default message which contains item info to seller
        StringBuilder defaultMessage = new StringBuilder();
        defaultMessage.append("Hi, I am ").append(senderName).append(", and I am interested in the product: ").append(itemName);
        messageRepository.insert(defaultMessage.toString(), senderName, receiverName, LocalDateTime.parse(localDateTime.format(formatter), formatter), chatId);

        // step3: send a custom message(content) to seller
        messageRepository.insert(content, senderName, receiverName, LocalDateTime.parse(localDateTime.format(formatter), formatter), chatId);
    }


    public void reply(String content, Long chatId, String senderName) {
        // get current time and format it
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        LocalDateTime localDateTime = LocalDateTime.now();
        // step1: use chatEntity to get receiver name
        ChatEntity chatEntity = chatRepository.getChatEntityById(chatId);
        String receiverName = null;
        if(chatEntity.username1().equals(senderName)) {
            receiverName = chatEntity.username2();
        } else {
            receiverName = chatEntity.username1();
        }
        // step2: construct message entity and insert it into message repository
        messageRepository.insert(content, senderName, receiverName, LocalDateTime.parse(localDateTime.format(formatter), formatter), chatId);
    }


    public List<ChatEntity> getAllChats(String username) {
        return chatRepository.findChatEntitiesByUsername1OrUsername2(username);
    }

    public List<MessageEntity> getAllMessages(Long chatId) {
        return messageRepository.getMessageEntitiesByChatIdOrderBySendTimeAsc(chatId);
    }

}
