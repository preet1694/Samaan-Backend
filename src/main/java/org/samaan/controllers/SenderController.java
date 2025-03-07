// File: backend/src/main/java/com/example/deliveryapp/controller/SenderController.java
package org.samaan.controllers;

import org.samaan.model.Sender;
import org.samaan.services.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/senders")
public class SenderController {

    @Autowired
    private SenderService senderService;

    @PostMapping
    public ResponseEntity<?> createSender(@RequestBody Sender sender) {
        Sender createdSender = senderService.createSender(sender);
        return ResponseEntity.ok(createdSender);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSender(@PathVariable String id, @RequestBody Sender sender) {
        sender.setId(id);
        Sender updatedSender = senderService.updateSender(sender);
        return ResponseEntity.ok(updatedSender);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getSenderByUserId(@PathVariable String userId) {
        Sender sender = senderService.getSenderByUserId(userId);
        return ResponseEntity.ok(sender);
    }
}