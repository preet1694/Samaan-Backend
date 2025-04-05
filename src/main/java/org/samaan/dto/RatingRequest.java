package org.samaan.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RatingRequest {

    private String tripId;
    private Integer rating;
    private String feedback;

}
