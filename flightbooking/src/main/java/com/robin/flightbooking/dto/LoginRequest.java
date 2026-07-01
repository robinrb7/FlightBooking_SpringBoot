package com.robin.flightbooking.dto;
// dto -> Data Transfer Object, made for representing data coming from or going to client.


public class LoginRequest{
    private String email;
    private String password;

    public String getEmail(){ return email;}
    public String getPassword(){ return password;}
}