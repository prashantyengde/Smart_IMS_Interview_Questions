package com.example.cabBooking.service;



import com.example.cabBooking.entity.Driver;
import com.example.cabBooking.entity.RideDetails;
import com.example.cabBooking.entity.User;
import com.example.cabBooking.repository.DriverRepository;
import com.example.cabBooking.repository.RideDetailsRepository;
import com.example.cabBooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideService {

    private static final double FARE_PER_KM = 25.0;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private RideDetailsRepository rideDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    // Task 1: Book a ride
    public RideDetails bookRide(Long userId, String startLocation, String endLocation, double distance) {
        List<Driver> availableDrivers = driverRepository.findAll()
                .stream()
                .filter(Driver::isAvailability)
                .toList();

        if (availableDrivers.isEmpty()) {
            throw new RuntimeException("No available drivers");
        }

        Driver driver = availableDrivers.get(0);
        driver.setAvailability(false);
        driverRepository.save(driver);

        double fare = calculateFare(distance);

        RideDetails ride = new RideDetails();
        ride.setUserId(userId);
        ride.setDriverId(driver.getId());
        ride.setStartLocation(startLocation);
        ride.setEndLocation(endLocation);
        ride.setFare(fare);
        ride.setRideStatus("Ongoing");
        ride.setStartTime(LocalDateTime.now());

        return rideDetailsRepository.save(ride);
    }

    // Method to calculate fare based on distance
    private double calculateFare(double distance) {
        return distance * FARE_PER_KM;
    }

    // Task 2: Get recent completed rides (last 30 minutes)
    public List<RideDetails> getRecentCompletedRides(Long userId) {
        LocalDateTime thirtyMinutesAgo = LocalDateTime.now().minusMinutes(30);

        return rideDetailsRepository.findByUserId(userId)
                .stream()
                .filter(r -> "Completed".equals(r.getRideStatus()))
                .filter(r -> r.getStartTime() != null && r.getStartTime().isAfter(thirtyMinutesAgo))
                .toList();
    }

    // Task 3: End a ride
    public RideDetails endRide(Long rideId) {
        RideDetails ride = rideDetailsRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found"));

        ride.setRideStatus("Completed");
        ride.setEndTime(LocalDateTime.now());

        Driver driver = driverRepository.findById(ride.getDriverId()).orElse(null);
        if (driver != null) {
            driver.setAvailability(true);
            driverRepository.save(driver);
        }

        return rideDetailsRepository.save(ride);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get all drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Get all ride details
    public List<RideDetails> getAllRideDetails() {
        return rideDetailsRepository.findAll();
    }
}