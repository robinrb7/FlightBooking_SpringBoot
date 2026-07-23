package com.robin.flightbooking.service;

import com.robin.flightbooking.dto.requestdto.AddFlightRequest;
import com.robin.flightbooking.dto.requestdto.SearchRequest;
import com.robin.flightbooking.dto.responsedto.SearchFlightsResponse;
import com.robin.flightbooking.entities.Flight;
import com.robin.flightbooking.exception.NoAvailableFlightsException;
import com.robin.flightbooking.repository.FlightRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class FlightService {
    private FlightRepository flightRepository;

    public String addFlight(AddFlightRequest flight){

        Flight flightToAdd = new Flight(
                flight.getFlightId(),
                flight.getSource(),
                flight.getDestination(),
                flight.getDepartureTime(),
                flight.getDate(),
                flight.getAvailableSeats(),
                flight.getBaseFare()
        );

        flightRepository.save(flightToAdd);
        log.info("Flight {} added successfully. {} -> {} on {}",
                flight.getFlightId(), flight.getSource(), flight.getDestination(), flight.getDate());

        return "Flight added successfully";
    }

    public List<SearchFlightsResponse> searchFlight(String src, String dest, LocalDate date){
        log.debug("Searching flights. Source={}, Destination={}, Date={}",
                src, dest, date);

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
            }
        }

        log.info("Found {} flight(s) from {} to {} on {}",
                result.size(), src, dest, date);

        if(result.isEmpty()){
            log.warn("No flight Available from {} to {} on {}",
                    src,dest,date);

            throw new NoAvailableFlightsException("There are no available flights available from "
                                        + src + " -> " + dest + " on " + date);
        }
        return result;
    }

}
