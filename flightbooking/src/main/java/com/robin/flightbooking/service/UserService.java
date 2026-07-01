package com.robin.flightbooking.service;
//services handles all the business logic


import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public void signUp(User user){
        User existing = userRepository.findByEmail(user.getEmail());

        if(existing != null){
            System.out.println("Email already registered.");
        }

        userRepository.save(user);
        System.out.println("Registration successful.");
    }

    public void login(String email, String password){
        User user = userRepository.findByEmail(email);

        if(user == null){
            System.out.println("Email not registered.Try Signing Up");
        }

        System.out.println("Successfully logged in!");
    }


}
