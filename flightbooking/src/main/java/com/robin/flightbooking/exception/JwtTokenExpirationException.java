package com.robin.flightbooking.exception;

public class JwtTokenExpirationException extends RuntimeException{
    public JwtTokenExpirationException(String message){
        super(message);
    }
}
