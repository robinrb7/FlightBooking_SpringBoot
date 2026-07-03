package com.robin.flightbooking.exception;

public class SeatNotAvailableException extends RuntimeException{
    public SeatNotAvailableException(String message){
        super(message);
    }
}
