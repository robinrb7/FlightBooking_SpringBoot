package com.robin.flightbooking.exception;

public class NoAvailableFlightsException extends RuntimeException{

    public NoAvailableFlightsException(String message){
        super(message);
    }
}
