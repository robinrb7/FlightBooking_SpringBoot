package com.robin.flightbooking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelRequest {

    @NotBlank(message = "Booking Id is required to cancel your booking.")
    private String bookingId;

}
