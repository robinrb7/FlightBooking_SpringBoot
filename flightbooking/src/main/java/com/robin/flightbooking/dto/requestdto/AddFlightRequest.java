package com.robin.flightbooking.dto.requestdto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AddFlightRequest {

    @NotNull(message = "Flight ID is required")
    @NotBlank(message = "Flight ID is required")
    private String flightId;

    @NotBlank(message = "Source is required")
    private String source;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Flight Departure time is required")
    private LocalTime departureTime;

    @NotNull(message = "Flight departure date is required")
    private LocalDate date;

    @NotNull(message = "Available Seats of flight is required")
    private Integer availableSeats;

    @NotNull(message = "Base Fare of flight is required")
    private double baseFare;
}
