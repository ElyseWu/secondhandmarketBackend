package team3.secondhand.controller;


import org.springframework.web.bind.annotation.*;
import team3.secondhand.entity.ChatEntity;
import team3.secondhand.entity.MessageEntity;
import team3.secondhand.service.ChatService;

import java.security.Principal;
import java.util.List;

@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ask/{item_id}")
    public void askForSeller(
            @PathVariable("item_id") Long itemId,
            @RequestParam("content") String content,
            Principal principal) {
        chatService.askForSeller(itemId, principal.getName(), content);
    }

    @PostMapping("/reply/{chat_id}")
    public void reply(
            @PathVariable("chat_id") Long chatId,
            @RequestParam("content") String content,
            Principal principal) {
        chatService.reply(content, chatId, principal.getName());
    }

    @GetMapping("/chats")
    public List<ChatEntity> getAllChats(Principal principal) {
        return chatService.getAllChats(principal.getName());
    }

    @GetMapping("/messages/{chat_id}")
    public List<MessageEntity> getAllMessages(@PathVariable("chat_id") Long chatId) {
        return chatService.getAllMessages(chatId);
    }

}
