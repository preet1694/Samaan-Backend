package org.samaan.services;

import org.samaan.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.samaan.model.Message;
import org.samaan.repositories.ChatRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;

    public void saveMessage(Message message) {

        System.out.println("Saving Message : "+message);

        if (message.getSenderEmail() == null || message.getCarrierEmail() == null) {
            throw new IllegalArgumentException("SenderEmail and CarrierEmail cannot be null");
        }
        if (message.getTimestamp() == null) {
            message.setTimestamp(LocalDateTime.now());
        }
        chatRepository.save(message);
    }

    public List<Message> getChatHistory(String roomId) {
        return chatRepository.findByRoomId(roomId);
    }

    public Map<String, List<Message>> getChatsGroupedBySender(String carrierEmail) {
        List<Message> messages = messageRepository.findByCarrierEmail(carrierEmail);

        // Group messages by senderEmail
        return messages.stream()
                .collect(Collectors.groupingBy(Message::getSenderEmail));
    }

    public List<String> getAllChatRooms(String email) {
        List<Message> messages = messageRepository.findBySenderEmailOrCarrierEmail(email, email);

        // Extract unique room IDs
        return messages.stream()
                .map(Message::getRoomId)
                .distinct()
                .collect(Collectors.toList());
    }

}

