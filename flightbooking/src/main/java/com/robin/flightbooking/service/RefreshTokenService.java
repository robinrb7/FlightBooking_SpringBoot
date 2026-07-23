package com.robin.flightbooking.service;

import com.robin.flightbooking.entities.RefreshToken;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.ExpiredRefreshTokenException;
import com.robin.flightbooking.exception.InvalidRefreshTokenException;
import com.robin.flightbooking.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Value("${refreshTokenValidity}")
    private Integer REFRESH_TOKEN_VALIDITY_DAYS;

    @Transactional
    private RefreshToken issueRefreshToken(User user){

        deleteRefreshToken(user); // delete the existing refresh token and create a new one.

        String token = UUID.randomUUID().toString(); //create new refresh token
        RefreshToken refreshToken = new RefreshToken(
                token,
                user,
                LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    private RefreshToken validateRefreshToken(String token){
        //if the token is valid -> then it has to be in the db + it should not be expired.

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException("Invalid Refresh Token"));

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){  //already expired
            refreshTokenRepository.delete(refreshToken);
            log.warn("Refresh token is expired for userId: {}", refreshToken.getUser().getUserId());
            throw new ExpiredRefreshTokenException("Refresh Token is expired. User needs to log in");
        }

        return refreshToken;
    }

    public RefreshToken createRefreshToken(User user){

        return issueRefreshToken(user);
    }


    @Transactional
    public RefreshToken rotateRefreshToken(String token) {
        RefreshToken refreshToken = validateRefreshToken(token);

        return issueRefreshToken(refreshToken.getUser());
    }

    public void deleteRefreshToken(User user){
        RefreshToken token = refreshTokenRepository.findByUser(user);

        if(token != null){
         refreshTokenRepository.delete(token);
         refreshTokenRepository.flush();
        }

    }
}
