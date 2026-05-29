
package com.example.cabBooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cabBooking.entity.Driver;
import com.example.cabBooking.entity.RideDetails;
import com.example.cabBooking.entity.User;
import com.example.cabBooking.service.RideService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired
    private RideService rideService;

    // Endpoint to book a ride
    @PostMapping("/book")
    public ResponseEntity<RideDetails> bookRide(
            @RequestParam Long userId,
            @RequestParam String startLocation,
            @RequestParam String endLocation,
            @RequestParam double distance) {
        RideDetails rideDetails = rideService.bookRide(userId, startLocation, endLocation, distance);
        return new ResponseEntity<>(rideDetails, HttpStatus.CREATED);
    }

    // Endpoint to get recent completed rides for a user
    @GetMapping("/completed")
    public ResponseEntity<List<RideDetails>> getRecentCompletedRides(@RequestParam Long userId) {
        List<RideDetails> completedRides = rideService.getRecentCompletedRides(userId);
        if (completedRides.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(completedRides, HttpStatus.OK);
    }

    // Endpoint to end a ride
    @PostMapping("/end/{rideId}")
    public ResponseEntity<RideDetails> endRide(@PathVariable Long rideId) {
        RideDetails rideDetails = rideService.endRide(rideId);
        return new ResponseEntity<>(rideDetails, HttpStatus.OK);
    }

    // Endpoint to get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = rideService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Endpoint to get all drivers
    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = rideService.getAllDrivers();
        if (drivers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    // Endpoint to get all ride details
    @GetMapping("/ride-details")
    public ResponseEntity<List<RideDetails>> getAllRideDetails() {
        List<RideDetails> rideDetails = rideService.getAllRideDetails();
        if (rideDetails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rideDetails, HttpStatus.OK);
    }
}