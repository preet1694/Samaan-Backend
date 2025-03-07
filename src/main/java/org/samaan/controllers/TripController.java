package org.samaan.controllers;

import org.samaan.model.Trip;
import org.samaan.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    // ✅ Add a new trip while fetching carrierName from users collection
    @PostMapping("/add")
    public ResponseEntity<Trip> addTrip(@Valid @RequestBody Trip trip) {
        Trip savedTrip = tripService.addTrip(trip);  // Ensure this method sets carrierName
        return ResponseEntity.ok(savedTrip);
    }

    // ✅ Fetch all trips
    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    // ✅ Fetch trips by email
    @GetMapping("/getusertrips")
    public ResponseEntity<List<Trip>> getTripsByEmail(@RequestParam String storedEmail) {
        List<Trip> trips = tripService.getTripsByEmail(storedEmail);
        if (trips.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trips);
    }

    // ✅ Search trips by source, destination, and date
    @GetMapping("/search")
    public List<Trip> searchTrips(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam String date) {
        return tripService.searchTrips(source, destination, date);
    }
}
