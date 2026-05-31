package com.flight.dao;

import com.flight.entities.Flight;
import jakarta.persistence.*;
import java.util.List;

public class FlightDAO {

    private EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("flightPU");

    public int addFlight(Flight flight) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(flight);
            em.getTransaction().commit();
            return flight.getId();
        } finally {
            em.close();
        }
    }

    public List<Flight> getAllFlights() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Flight> query = 
                em.createQuery("SELECT f FROM Flight f", Flight.class);
            List<Flight> result = query.getResultList();
            return result.isEmpty() ? null : result;
        } finally {
            em.close();
        }
    }

    public Flight findByFlightNumber(String flightNumber) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Flight> query = em.createQuery(
                "SELECT f FROM Flight f WHERE f.flightNumber = :fn", Flight.class);
            query.setParameter("fn", flightNumber);
            List<Flight> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public void updateFlightPrice(String flightNumber, double newPrice) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery(
                "UPDATE Flight f SET f.price = :price WHERE f.flightNumber = :fn");
            query.setParameter("price", newPrice);
            query.setParameter("fn", flightNumber);
            query.executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}