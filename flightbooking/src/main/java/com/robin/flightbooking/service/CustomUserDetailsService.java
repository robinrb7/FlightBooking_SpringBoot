package com.robin.flightbooking.service;

import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.UserNotFoundException;
import com.robin.flightbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user==null){
            log.warn("No user was found with email= {}", email);
            throw new UserNotFoundException("User not found");
        }

        return user;
    }
}
