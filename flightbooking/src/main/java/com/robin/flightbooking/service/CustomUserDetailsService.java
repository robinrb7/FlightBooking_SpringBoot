package com.robin.flightbooking.service;

import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.UserNotFoundException;
import com.robin.flightbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new UserNotFoundException("User not found");
        }

        return user;
    }
}
