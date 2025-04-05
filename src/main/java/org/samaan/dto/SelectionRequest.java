package org.samaan.dto;

public class SelectionRequest {
    private String senderEmail;
    private String tripId;

    public SelectionRequest() {
    }

    public SelectionRequest(String senderEmail, String tripId) {
        this.senderEmail = senderEmail;
        this.tripId = tripId;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
