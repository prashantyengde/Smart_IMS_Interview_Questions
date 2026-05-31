package com.flight;

import com.flight.entities.Flight;
import com.flight.service.FlightService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class FlightApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightService flightService = new FlightService();

        while (true) {
            System.out.println("\nFlight Management System");
            System.out.println("1. Add Flight");
            System.out.println("2. View All Flights");
            System.out.println("3. Find Flight by Flight Number");
            System.out.println("4. Update Flight Price");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1: addFlight(scanner, flightService); break;
                    case 2: viewAllFlights(flightService); break;
                    case 3: findFlightByFlightNumber(scanner, flightService); break;
                    case 4: updateFlightPrice(scanner, flightService); break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static void addFlight(Scanner scanner, FlightService flightService) {
        System.out.print("Enter Flight Number: ");
        String flightNumber = scanner.nextLine();

        System.out.print("Enter Departure Airport: ");
        String departureAirport = scanner.nextLine();

        System.out.print("Enter Arrival Airport: ");
        String arrivalAirport = scanner.nextLine();

        LocalDateTime departureTime = getLocalDateTimeInput(scanner, 
            "Enter Departure Time (yyyy-MM-dd HH:mm): ");
        LocalDateTime arrivalTime = getLocalDateTimeInput(scanner, 
            "Enter Arrival Time (yyyy-MM-dd HH:mm): ");

        System.out.print("Enter Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter Available Seats: ");
        int availableSeats = Integer.parseInt(scanner.nextLine());

        Flight newFlight = new Flight(flightNumber, departureAirport, arrivalAirport,
                                      departureTime, arrivalTime, price, availableSeats);
        try {
            flightService.addFlight(newFlight);
            System.out.println("Flight added successfully!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static LocalDateTime getLocalDateTimeInput(Scanner scanner, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date-time format. Please use yyyy-MM-dd HH:mm");
            }
        }
    }

    private static void viewAllFlights(FlightService flightService) {
        try {
            List<Flight> flights = flightService.getAllFlights();
            if (flights == null || flights.isEmpty()) {
                System.out.println("No Flights Found.");
            } else {
                flights.forEach(System.out::println);
            }
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findFlightByFlightNumber(Scanner scanner, FlightService flightService) {
        System.out.print("Enter Flight Number to find: ");
        String flightNumber = scanner.nextLine();
        try {
            Flight flight = flightService.findByFlightNumber(flightNumber);
            if (flight != null) {
                System.out.println(flight);
            } else {
                System.out.println("No flight found with given Flight Number.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateFlightPrice(Scanner scanner, FlightService flightService) {
        System.out.print("Enter Flight Number of the flight to update price: ");
        String flightNumber = scanner.nextLine();
        System.out.print("Enter the new price: ");
        try {
            double newPrice = Double.parseDouble(scanner.nextLine());
            flightService.updateFlightPrice(flightNumber, newPrice);
            System.out.println("Flight price updated successfully!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}