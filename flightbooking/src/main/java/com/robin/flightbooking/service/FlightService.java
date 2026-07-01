package com.robin.flightbooking.service;

import com.robin.flightbooking.entities.Flight;
import com.robin.flightbooking.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlightService {
    private FlightRepository flightRepository;

    public  FlightService(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }

    public void addFlight(Flight flight){
        flightRepository.save(flight);
    }

    public List<Flight> searchFlight(String src, String dest, LocalDate date){
        List<Flight> result = new ArrayList<>();
        List<Flight> allFlights = flightRepository.findAll();

        for(Flight flight: allFlights){

            if( flight.getSource().equalsIgnoreCase(src) &&
                flight.getDestination().equalsIgnoreCase(dest) &&
                flight.getDate().equals(date)
            ) result.add(flight);
        }

        return result;
    }




}
