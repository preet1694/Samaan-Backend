package org.samaan.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.samaan.services.GeocodingService;
import org.samaan.model.LatLng;
import org.samaan.services.DistanceCalculator;
@RestController
@RequestMapping("/distance")
public class DistanceController {

    @Autowired
    private GeocodingService geocodingService;

    @GetMapping
    public ResponseEntity<String> getDistance(@RequestParam String location1, @RequestParam String location2) {
        LatLng coordinates1 = geocodingService.getCoordinates(location1);
        LatLng coordinates2 = geocodingService.getCoordinates(location2);

        if (coordinates1 == null || coordinates2 == null) {
            return ResponseEntity.badRequest().body("Unable to fetch coordinates for one or both locations.");
        }

        double distance = DistanceCalculator.calculateDistance(
                coordinates1.getLat(), coordinates1.getLng(),
                coordinates2.getLat(), coordinates2.getLng()
        )*1.2;
        int cost = (int) (distance * 0.38);
        System.out.println(distance);
        System.out.println(cost);
        return ResponseEntity.ok("Distance :" + distance + "\nCost :" + cost );

    }
}
