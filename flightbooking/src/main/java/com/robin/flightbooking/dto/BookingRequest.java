package com.robin.flightbooking.dto;

import com.robin.flightbooking.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookingRequest {

    @NotBlank(message = "Flight Id is required")
    private String flightId;

    @NotNull(message = "User is required")
    private User user;

}
