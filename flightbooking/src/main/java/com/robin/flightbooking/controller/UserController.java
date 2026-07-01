package com.robin.flightbooking.controller;

import com.robin.flightbooking.dto.LoginRequest;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.service.UserService;
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
    public void signUp(@RequestBody User user){
        userService.signUp(user);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest){
        userService.login(loginRequest.getEmail(),loginRequest.getPassword());
    }
}
