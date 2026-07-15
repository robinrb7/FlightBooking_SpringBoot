package com.robin.flightbooking.service;
//services handles all the business logic


import com.robin.flightbooking.dto.requestdto.RefreshTokenRequest;
import com.robin.flightbooking.dto.responsedto.LoginResponseDto;
import com.robin.flightbooking.dto.responsedto.RefreshTokenResponse;
import com.robin.flightbooking.entities.RefreshToken;
import com.robin.flightbooking.entities.RoleEnum;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.EmailAlreadyExistsException;
import com.robin.flightbooking.exception.UserNotFoundException;
import com.robin.flightbooking.exception.InvalidPasswordException;
import com.robin.flightbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;


    public String signUp(User user){
        User existing = userRepository.findByEmail(user.getEmail());
        if(existing != null){
            throw new EmailAlreadyExistsException("Email is already registered.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(RoleEnum.ROLE_USER);
        userRepository.save(user);
        return "User registered successful";
    }

    public LoginResponseDto login(String email, String password){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user = (User)authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        String accessToken = jwtService.generateAccessToken(user);

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

        RefreshToken refreshToken = refreshTokenService.validateRefreshToken(token);
        String accessToken = jwtService.generateAccessToken(refreshToken.getUser());

        return new RefreshTokenResponse(accessToken);
    }


    public String logout(){
        User user = getCurrentUser();
        refreshTokenService.deleteRefreshToken(user);

        return "Logged Out Successfully";
    }



}
