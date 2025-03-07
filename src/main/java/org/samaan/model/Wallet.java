package org.samaan.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "wallets")
public class Wallet {
    @Id
    private String id;
    private String userId;
    private double balance;
    private String currency;
    private String createdAt;
    private String updatedAt;
}