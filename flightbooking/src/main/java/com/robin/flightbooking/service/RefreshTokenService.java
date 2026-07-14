package com.robin.flightbooking.service;

import com.robin.flightbooking.entities.RefreshToken;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.ExpiredRefreshTokenException;
import com.robin.flightbooking.exception.InvalidRefreshTokenException;
import com.robin.flightbooking.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final long REFRESH_TOKEN_VALIDITY_DAYS = 7;

    public RefreshToken createRefreshToken(User user){
        RefreshToken alreadyExistingToken = refreshTokenRepository.findByUser(user); //logging in again within time span of already existing refersh token
        if(alreadyExistingToken != null){
            deleteRefreshToken(user); // delete the existing refersht token and create a new one.
        }

        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(
                token,
                user,
                LocalDateTime.now().plusDays(REFRESH_TOKEN_VALIDITY_DAYS)
        );

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken validateRefreshToken(String token) {

        //if the token is valid -> then it has to be in the db + it should not be expired.

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException("Invalid Refresh Token"));

        if(refreshToken.getExpiryDate().isBefore(LocalDateTime.now())){  //already expired
            refreshTokenRepository.delete(refreshToken);
            throw new ExpiredRefreshTokenException("Refresh Token is expired. User needs to log in");
        }

        return refreshToken;
    }

    public void deleteRefreshToken(User user){
        refreshTokenRepository.deleteByUser(user);

    }
}
