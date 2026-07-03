package com.robin.flightbooking.dto;
// dto -> Data Transfer Object, made for representing data coming from or going to client.

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest{
    private String email;
    private String password;


}