package com.robin.flightbooking.service;

import com.robin.flightbooking.dto.requestdto.AddFlightRequest;
import com.robin.flightbooking.dto.requestdto.SearchRequest;
import com.robin.flightbooking.dto.responsedto.SearchFlightsResponse;
import com.robin.flightbooking.entities.Flight;
import com.robin.flightbooking.exception.NoAvailableFlightsException;
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



    public String addFlight(AddFlightRequest flight){

        System.out.println("Inside add flight method in flight service");

        Flight flightToAdd = new Flight(
                flight.getFlightId(),
                flight.getSource(),
                flight.getDestination(),
                flight.getDepartureTime(),
                flight.getDate(),
                flight.getAvailableSeats(),
                flight.getBaseFare()
        );

        System.out.println("created the flight to add method in flight service");

        flightRepository.save(flightToAdd);

        System.out.println("Added the flight method in flight service");

        return "Flight added successfully";
    }

    public List<SearchFlightsResponse> searchFlight(String src, String dest, LocalDate date){
        List<SearchFlightsResponse> result = new ArrayList<>();
        List<Flight> allFlights = flightRepository.findAll();

        for(Flight flight: allFlights){

            if( flight.getSource().equalsIgnoreCase(src) &&
                flight.getDestination().equalsIgnoreCase(dest) &&
                flight.getDate().equals(date)
            ) {
                SearchFlightsResponse flightsResponse = new SearchFlightsResponse(
                        flight.getFlightId(),
                        flight.getSource(),
                        flight.getDestination(),
                        flight.getDepartureTime(),
                        flight.getDate(),
                        flight.getAvailableSeats(),
                        flight.getBaseFare()
                );
                result.add(flightsResponse);
            };
        }

        if(result.isEmpty()){
            throw new NoAvailableFlightsException("There are no available flights available from "
                                        + src + " -> " + dest + " on " + date);
        }
        return result;
    }

}
