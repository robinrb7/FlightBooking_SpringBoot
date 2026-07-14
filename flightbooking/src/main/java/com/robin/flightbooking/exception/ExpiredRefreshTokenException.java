package com.robin.flightbooking.exception;

public class ExpiredRefreshTokenException extends RuntimeException{
    public ExpiredRefreshTokenException(String message){
        super(message);
    }
}
