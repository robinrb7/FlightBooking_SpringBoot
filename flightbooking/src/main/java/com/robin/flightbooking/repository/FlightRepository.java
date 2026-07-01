package com.robin.flightbooking.repository;

import com.robin.flightbooking.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight,String> {
    Flight findByFlightId(String flightId);
}
