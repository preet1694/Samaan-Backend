package org.samaan.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "messages")
public class Message {
    @Id
    private String id;
    private String roomId;
    private String senderEmail;
    private String carrierEmail;
    private String message;
    private boolean read;
    private LocalDateTime timestamp;

    public Message() {}

    public Message(String senderEmail, String carrierEmail, String message, String roomId, boolean read, LocalDateTime timestamp) {
        this.roomId = roomId;
        this.read=read;
        this.senderEmail = senderEmail;
        this.carrierEmail = carrierEmail;
        this.message = message;
        this.timestamp = timestamp;
    }
}
