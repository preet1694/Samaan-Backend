package org.samaan.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.util.List;

@Setter
@Getter
@Document(collection = "rooms")
public class Room {
    // Getters and Setters
    @Id
    private String roomId;
    private List<Message> messages;
    private String senderEmail;
    private String carrierEmail;

    // Constructor
    public Room(String roomId, List<Message> messages, String senderEmail, String carrierEmail) {
        this.roomId = roomId;
        this.messages = messages;
        this.senderEmail = senderEmail;
        this.carrierEmail = carrierEmail;
    }

    public Room() {

    }

}
