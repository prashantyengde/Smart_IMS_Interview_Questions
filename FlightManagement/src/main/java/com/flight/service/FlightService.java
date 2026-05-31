package com.flight.service;

import com.flight.dao.FlightDAO;
import com.flight.entities.Flight;
import java.util.List;

public class FlightService {

    private FlightDAO flightDAO = new FlightDAO();

    public int addFlight(Flight flight) {
        validateFlight(flight);
        try {
            return flightDAO.addFlight(flight);
        } catch (Exception e) {
            throw new IllegalStateException("Database error: " + e.getMessage());
        }
    }

    public List<Flight> getAllFlights() {
        try {
            return flightDAO.getAllFlights();
        } catch (Exception e) {
            throw new IllegalStateException("Database error: " + e.getMessage());
        }
    }

    public Flight findByFlightNumber(String flightNumber) {
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number is required");
        }
        try {
            return flightDAO.findByFlightNumber(flightNumber);
        } catch (Exception e) {
            throw new IllegalStateException("Database error: " + e.getMessage());
        }
    }

    public void updateFlightPrice(String flightNumber, double newPrice) {
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number is required");
        }
        if (newPrice < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }
        try {
            flightDAO.updateFlightPrice(flightNumber, newPrice);
        } catch (Exception e) {
            throw new IllegalStateException("Database error: " + e.getMessage());
        }
    }

    private void validateFlight(Flight flight) {
        if (flight.getFlightNumber() == null || flight.getFlightNumber().trim().isEmpty())
            throw new IllegalArgumentException("Flight number is required");

        if (flight.getDepartureAirport() == null || flight.getDepartureAirport().trim().isEmpty())
            throw new IllegalArgumentException("Departure airport is required");

        if (flight.getArrivalAirport() == null || flight.getArrivalAirport().trim().isEmpty())
            throw new IllegalArgumentException("Arrival airport is required");

        if (flight.getDepartureTime() == null)
            throw new IllegalArgumentException("Departure time is required");

        if (flight.getArrivalTime() == null)
            throw new IllegalArgumentException("Arrival time is required");

        if (!flight.getArrivalTime().isAfter(flight.getDepartureTime()))
            throw new IllegalArgumentException("Arrival time must be after departure time");

        if (flight.getPrice() < 0)
            throw new IllegalArgumentException("Price must be non-negative");

        if (flight.getAvailableSeats() < 0)
            throw new IllegalArgumentException("Available seats must be non-negative");
    }
}