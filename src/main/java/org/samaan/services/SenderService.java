// File: backend/src/main/java/com/example/deliveryapp/service/SenderService.java
package org.samaan.services;

import org.samaan.model.Sender;
import org.samaan.repositories.SenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SenderService {

    @Autowired
    private SenderRepository senderRepository;

    public Sender createSender(Sender sender) {
        sender.setCreatedAt(LocalDateTime.now().toString());
        sender.setUpdatedAt(LocalDateTime.now().toString());
        return senderRepository.save(sender);
    }

    public Sender updateSender(Sender sender) {
        sender.setUpdatedAt(LocalDateTime.now().toString());
        return senderRepository.save(sender);
    }

    public Sender getSenderByUserId(String userId) {
        return senderRepository.findByUserId(userId);
    }
}