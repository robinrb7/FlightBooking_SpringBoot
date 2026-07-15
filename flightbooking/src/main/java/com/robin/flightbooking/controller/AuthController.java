package com.robin.flightbooking.controller;

import com.robin.flightbooking.dto.requestdto.LoginRequest;
import com.robin.flightbooking.dto.requestdto.RefreshTokenRequest;
import com.robin.flightbooking.dto.requestdto.SignupRequest;
import com.robin.flightbooking.dto.responsedto.LoginResponseDto;
import com.robin.flightbooking.dto.responsedto.RefreshTokenResponse;
import com.robin.flightbooking.entities.RefreshToken;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;  // final -> makes the object immutable

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequest signupRequest){

        System.out.println("Signup APi is hit.");
        User user = new User(
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                signupRequest.getPhoneNumber(),
                signupRequest.getPassword()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signUp(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity
                .ok(authService.login(loginRequest.getEmail(),loginRequest.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return ResponseEntity
                .ok(authService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        System.out.println("Logout Controller");
        return ResponseEntity
                .ok(authService.logout());
    }
}
