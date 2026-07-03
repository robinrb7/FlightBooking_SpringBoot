package com.robin.flightbooking.exception;

public class NoAvailableFlights extends RuntimeException{

    public NoAvailableFlights(String message){
        super(message);
    }
}
