package com.robin.flightbooking.controller;

import com.robin.flightbooking.dto.LoginRequest;
import com.robin.flightbooking.dto.SignupRequest;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;  // final -> makes the object immutable
    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequest signupRequest){

        User user = new User(
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                signupRequest.getPhoneNumber(),
                signupRequest.getPassword()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signUp(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity
                .ok(userService.login(loginRequest.getEmail(),loginRequest.getPassword()));
    }
}
