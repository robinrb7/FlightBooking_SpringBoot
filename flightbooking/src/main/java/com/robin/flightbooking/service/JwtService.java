package com.robin.flightbooking.service;

import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.InvalidJwtException;
import com.robin.flightbooking.exception.JwtTokenExpirationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;


@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey; //3rd part of JWT token

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(
                jwtSecretKey.getBytes(StandardCharsets.UTF_8)); //1st part of JWT token
    }

    public String generateAccessToken(User user){ // 2nd part of Jwt Token (payload from user)
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId",user.getUserId()) //use map to put multiple claims
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(5).toMillis()))
                .signWith(getSecretKey())
                .compact();
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserEmail(String token){
        try{
            return extractAllClaims(token).getSubject();
        }
        catch (ExpiredJwtException e) {
            throw new JwtTokenExpirationException("JWT Token expired");
        }
        catch (JwtException e) {
            throw new InvalidJwtException("Invalid JWT Token");
        }
    }

    private Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = extractUserEmail(token);

        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
