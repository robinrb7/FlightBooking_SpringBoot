package com.robin.flightbooking.dto;

import com.robin.flightbooking.entities.User;

public class BookingRequest {
    private String flightId;
    private User user;

    public String getFlightId() {
        return flightId;
    }
    public User getUser() {
        return user;
    }
}
