package com.robin.flightbooking.repository;

import com.robin.flightbooking.entities.RefreshToken;
import com.robin.flightbooking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
    public RefreshToken findByUser(User user);
    public void deleteByUser(User user);
    public Optional<RefreshToken> findByToken(String token);
}
