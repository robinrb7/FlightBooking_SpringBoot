package com.robin.flightbooking.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BookingRequest {

    @NotBlank(message = "Flight Id is required")
    private String flightId;

    @NotNull(message = "Email is required")
    @Email(message = "Email not correctly formated")
    private String email;

}
