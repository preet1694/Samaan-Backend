package org.samaan.controllers;

import org.samaan.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.samaan.model.Message;
import org.samaan.services.ChatService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/chat")

public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageRepository messageRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatService chatService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
    }

    @PostMapping("/save")
    public ResponseEntity<List<Message>> saveMessage(@RequestBody Message message) {
        chatService.saveMessage(message);
        List<Message> messages = chatService.getChatHistory(message.getRoomId());
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/history")
    public List<Message> getChatHistory(@RequestParam String roomId) {
        return messageRepository.findByRoomIdOrderByTimestampAsc(roomId);
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat")
    public void sendMessage(@Payload Message message) {
        message.setTimestamp(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

        if (message.getRoomId() == null || message.getSenderEmail() == null || message.getCarrierEmail() == null) {
            throw new IllegalArgumentException("roomId, senderEmail, and carrierEmail cannot be null");
        }

        chatService.saveMessage(message);

        String destination = "/topic/chat/" + message.getRoomId();
        simpMessagingTemplate.convertAndSend(destination, message);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<String>> getAllChatRooms(@RequestParam String email) {
        List<String> chatRooms = chatService.getAllChatRooms(email);
        return ResponseEntity.ok(chatRooms);
    }
}
