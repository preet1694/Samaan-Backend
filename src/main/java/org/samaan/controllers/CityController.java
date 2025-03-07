package org.samaan.controllers;


import org.samaan.services.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin(origins = "http://localhost:5173") // Allow frontend access
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<String>> searchCities(@RequestParam String query) {
        List<String> result = cityService.searchCities(query);
        return ResponseEntity.ok(result);
    }
}
