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
import org.samaan.services.NotificationService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private NotificationService notificationService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(SimpMessagingTemplate simpMessagingTemplate, ChatService chatService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatService = chatService;
    }

    // ✅ Save Message to Database
    @PostMapping("/save")
    public ResponseEntity<List<Message>> saveMessage(@RequestBody Message message) {
        chatService.saveMessage(message);
//        notificationService.sendEmailNotification(
//                message.getCarrierEmail(),
//                "New Chat Message",
//                "You have a new message from " + message.getSenderEmail()
//        );
            List<Message> messages = chatService.getChatHistory(message.getRoomId());
            return ResponseEntity.ok(messages);
    }

    // ✅ Get Chat History
    @GetMapping("/history")
    public List<Message> getChatHistory(@RequestParam String roomId) {
        return messageRepository.findByRoomIdOrderByTimestampAsc(roomId);
    }

    // ✅ WebSocket Message Handling
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Message message) {
        message.setTimestamp(LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()));

        // Validate message
        if (message.getRoomId() == null || message.getSenderEmail() == null || message.getCarrierEmail() == null) {
            throw new IllegalArgumentException("roomId, senderEmail, and carrierEmail cannot be null");
        }

        // Save the message to MongoDB
        chatService.saveMessage(message);

        // Manually send message to the correct topic
        String destination = "/topic/chat/" + message.getRoomId();
        simpMessagingTemplate.convertAndSend(destination, message);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<String>> getAllChatRooms(@RequestParam String email) {
        List<String> chatRooms = chatService.getAllChatRooms(email);
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<Message>>> getAllChats(@RequestParam String carrierEmail) {
        return ResponseEntity.ok(chatService.getChatsGroupedBySender(carrierEmail));
    }
}
