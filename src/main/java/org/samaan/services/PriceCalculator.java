package org.samaan.services;

import org.samaan.model.LatLng;
import org.springframework.stereotype.Service;

@Service
public class PriceCalculator {
    private static final double BASE_RATE_PER_KM = 0.30; // Price per km in INR
    private static final int MINIMUM_PRICE = 50; // Minimum charge

    private final GeocodingService geocodingService;

    public PriceCalculator(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    public int calculatePrice(String source, String destination) {
        LatLng sourceCoordinates = geocodingService.getCoordinates(source);
        LatLng destinationCoordinates = geocodingService.getCoordinates(destination);

        if (sourceCoordinates == null || destinationCoordinates == null) {
            throw new IllegalArgumentException("Invalid source or destination");
        }

        double distance = DistanceCalculator.calculateDistance(
                sourceCoordinates.getLat(), sourceCoordinates.getLng(),
                destinationCoordinates.getLat(), destinationCoordinates.getLng()
        );

        int price = (int) (distance * BASE_RATE_PER_KM);
        return Math.max(price, MINIMUM_PRICE); // Ensure minimum charge applies
    }
}

