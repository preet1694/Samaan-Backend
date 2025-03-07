package org.samaan.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "senders")
public class Sender {
    @Id
    private String id;
    private String userId;
    private String address;
    private String createdAt;
    private String updatedAt;
}