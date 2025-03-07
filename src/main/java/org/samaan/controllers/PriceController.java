package org.samaan.controllers;

import org.samaan.services.PriceCalculator;
import org.samaan.services.GeocodingService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/price")
public class PriceController {

    private final PriceCalculator priceCalculator;

    public PriceController(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    @GetMapping("/calculate")
    public Map<String, Double> calculatePrice(
            @RequestParam String source,
            @RequestParam String destination) {

        double price = priceCalculator.calculatePrice(source, destination);
        return Map.of("price", price);
    }
}
