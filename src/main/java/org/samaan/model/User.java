package org.samaan.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document(collection = "users") // Make sure this matches your MongoDB collection
public class User {
    @Id
    private String id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String email;
    private String password;
    @Setter
    @Getter
    private String role;
    private List<String> roomIds;


    public void setCreatedAt(String string) {
    }

    public void setUpdatedAt(String string) {
    }
}