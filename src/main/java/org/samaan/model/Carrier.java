package org.samaan.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "carriers")
public class Carrier {
    @Id
    private String id;
    private String userId;
    private String vehicleType;
    private double capacity;
    private String currentLocation;
    private boolean isAvailable;
    private String createdAt;
    private String updatedAt;
}