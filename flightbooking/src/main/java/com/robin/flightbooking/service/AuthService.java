package com.robin.flightbooking.service;
//services handles all the business logic


import com.robin.flightbooking.dto.requestdto.SignupRequest;
import com.robin.flightbooking.dto.responsedto.LoginResponseDto;
import com.robin.flightbooking.dto.responsedto.RefreshTokenResponse;
import com.robin.flightbooking.entities.RefreshToken;
import com.robin.flightbooking.entities.RoleEnum;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.EmailAlreadyExistsException;
import com.robin.flightbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class AuthService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;


    public String signUp(SignupRequest signupRequest){
        User user = new User(
                signupRequest.getFirstName(),
                signupRequest.getLastName(),
                signupRequest.getEmail(),
                signupRequest.getPhoneNumber(),
                signupRequest.getPassword()
        );

        User existing = userRepository.findByEmail(user.getEmail());
        if(existing != null){
            log.warn("User already registered with email : {}", user.getEmail());
            throw new EmailAlreadyExistsException("Email is already registered.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleEnum.ROLE_USER);
        userRepository.save(user);

        log.info("User registered with email: {}",user.getEmail());
        return "User registered successful";
    }

    public LoginResponseDto login(String email, String password){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user = (User)authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.generateAccessToken(user);

        log.info("User logged in with email: {}",user.getEmail());
        return new LoginResponseDto(
                accessToken,
                user.getEmail(),
                refreshToken.getToken());
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }

    public RefreshTokenResponse refreshToken(String token){

        RefreshToken refreshToken = refreshTokenService.rotateRefreshToken(token);
        String accessToken = jwtService.generateAccessToken(refreshToken.getUser());

        log.info("Access token refreshed for user {}", refreshToken.getUser().getEmail());

        return new RefreshTokenResponse(
                accessToken,
                refreshToken.getToken()
        );
    }


    public String logout(){
        User user = getCurrentUser();
        refreshTokenService.deleteRefreshToken(user);

        log.info("User with email {} logged out", user.getEmail());
        return "Logged Out Successfully";
    }



}
