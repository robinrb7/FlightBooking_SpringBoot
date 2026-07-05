package com.robin.flightbooking.controller;

import com.robin.flightbooking.dto.requestdto.SearchRequest;
import com.robin.flightbooking.entities.Flight;
import com.robin.flightbooking.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    public FlightController(FlightService flightService){this.flightService = flightService;}

    @PostMapping("/search")
    public ResponseEntity<?> searchFlight(@Valid @RequestBody SearchRequest searchRequest){
        return ResponseEntity
                .ok(flightService.searchFlight(
                searchRequest.getSource(),
                searchRequest.getDestination(),
                searchRequest.getDate()));
    }

    @PostMapping("/save")
    public void saveFlight(@RequestBody Flight flight){
        flightService.addFlight(flight);
    }


}
