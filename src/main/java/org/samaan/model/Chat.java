package org.samaan.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "chats")
public class Chat {
    @Id
    private String id;
    private List<String> participants;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}