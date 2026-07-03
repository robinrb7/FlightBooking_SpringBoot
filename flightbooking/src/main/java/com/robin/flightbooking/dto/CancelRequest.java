package com.robin.flightbooking.dto;

import jakarta.validation.constraints.NotBlank;

public class CancelRequest {

    @NotBlank(message = "Booking Id is required to cancel your booking.")
    private String bookingId;

    public String getBookingId() {
        return bookingId;
    }
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }
}
