package org.samaan.controllers;

import org.samaan.dto.RatingRequest;
import org.samaan.dto.SelectionRequest;
import org.samaan.model.Trip;
import org.samaan.repositories.TripRepository;
import org.samaan.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripRepository tripRepository;

    @PostMapping("/add")
    public ResponseEntity<Trip> addTrip(@Valid @RequestBody Trip trip) {
        Trip savedTrip = tripService.addTrip(trip); 
        return ResponseEntity.ok(savedTrip);
    }

    @PostMapping("/{tripId}/cancel")
    public ResponseEntity<?> requestCancel(@PathVariable String tripId, @RequestParam String role) {
        Optional<Trip> optionalTrip = tripRepository.findById(String.valueOf(tripId));
        if (optionalTrip.isEmpty()) return ResponseEntity.notFound().build();

        Trip trip = optionalTrip.get();

        if ("sender".equalsIgnoreCase(role)) {
            trip.setSenderRequestedCancel(true);
        } else if ("carrier".equalsIgnoreCase(role)) {
            trip.setCarrierRequestedCancel(true);
        }

        // If both have requested, finalize the cancellation
        if (trip.isSenderRequestedCancel() && trip.isCarrierRequestedCancel()) {
            trip.setIsCancelled(true);
            trip.setCancellationConfirmed(true);
        }

        tripRepository.save(trip);
        return ResponseEntity.ok(trip);
    }

    @PostMapping("/{tripId}/respond-cancel")
    public ResponseEntity<?> respondToCancel(@PathVariable Long tripId, @RequestParam String role, @RequestParam boolean agree) {
        Optional<Trip> optionalTrip = tripRepository.findById(String.valueOf(tripId));
        if (optionalTrip.isEmpty()) return ResponseEntity.notFound().build();

        Trip trip = optionalTrip.get();

        if (agree) {
            trip.setIsCancelled(true);
            trip.setCancellationConfirmed(true);
        } else {
            if ("sender".equalsIgnoreCase(role)) trip.setCarrierRequestedCancel(false);
            else if ("carrier".equalsIgnoreCase(role)) trip.setSenderRequestedCancel(false);
        }

        tripRepository.save(trip);
        return ResponseEntity.ok(trip);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Trip>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }
    
    @GetMapping("/getusertrips")
    public ResponseEntity<List<Trip>> getTripsByEmail(@RequestParam String storedEmail) {
        List<Trip> trips = tripService.getTripsByEmail(storedEmail);
        if (trips.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trips);
    }
    
    @GetMapping("/search")
    public List<Trip> searchTrips(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam String date) {
        return tripService.searchTrips(source, destination, date);
    }

    @PutMapping("/complete/{tripId}")
    public ResponseEntity<String> completeTrip(@PathVariable String tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isPresent()) {
            Trip trip = optionalTrip.get();
            trip.setCarrierCompleted(true); 
            tripRepository.save(trip);
            return ResponseEntity.ok("Trip marked as completed");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
    }

    @GetMapping("/sender/{senderEmail}")
    public ResponseEntity<List<Trip>> getTripsBySenderEmail(@PathVariable String senderEmail) {
        List<Trip> trips = tripRepository.findBySenderEmail(senderEmail);
        if (trips.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(trips);
    }

    @PostMapping("rating/{tripId}")
    public ResponseEntity<?> rateTrip(@PathVariable String tripId, @RequestBody RatingRequest request) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        if (trip.getRating() != null) {
            return ResponseEntity.badRequest().body("Trip is already rated.");
        }

        trip.setRating(request.getRating());
        trip.setFeedback(request.getFeedback());
        tripRepository.save(trip);

        return ResponseEntity.ok("Rating and feedback submitted successfully.");
    }

    @GetMapping("ratings/{senderEmail}")
    public List<Trip> getSenderRatings(@PathVariable String senderEmail) {
        return tripRepository.findBySenderEmailAndRatingNotNull(senderEmail);
    }

    @PostMapping("/select")
    public ResponseEntity<String> selectTrip(@RequestBody SelectionRequest request) {
        Optional<Trip> optionalTrip = tripRepository.findById(request.getTripId());
        if (optionalTrip.isPresent()) {
            Trip trip = optionalTrip.get();
            trip.setSenderEmail(request.getSenderEmail());
            trip.setSenderSelected(true);
            tripRepository.save(trip);
            return ResponseEntity.ok("Trip selected successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip not found");
    }

}
