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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;  // final -> makes the object immutable

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignupRequest signupRequest){
        log.info("Signup request received for email {}", signupRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signUp(signupRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequest loginRequest){
        log.info("Login request for {}",loginRequest.getEmail());
        return ResponseEntity
                .ok(authService.login(loginRequest.getEmail(),loginRequest.getPassword()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
        log.info("Refresh token request received");
        return ResponseEntity
                .ok(authService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        log.info("Logout request received");
        return ResponseEntity
                .ok(authService.logout());
    }
}
