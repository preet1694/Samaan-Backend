package org.samaan.services;

import org.samaan.model.Trip;
import org.samaan.model.User;
import org.samaan.repositories.TripRepository;
import org.samaan.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Automatically fetch carrierName based on email before saving the trip
    public Trip addTrip(Trip trip) {
        User user = userRepository.findByEmail(trip.getEmail());
        if (user == null) {
            throw new RuntimeException("User with email " + trip.getEmail() + " not found");
        }

        // Set the carrierName from the user
        trip.setCarrierName(user.getName());

        return tripRepository.save(trip);
    }

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public List<Trip> getTripsByEmail(String email) {
        return tripRepository.findByEmail(email);
    }

    public List<Trip> searchTrips(String source, String destination, String date) {
        return tripRepository.findBySourceAndDestinationAndDate(source, destination, date);
    }
}
