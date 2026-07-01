package com.robin.flightbooking.service;
//services handles all the business logic


import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void signUp(User user){
        User existing = userRepository.findByEmail(user.getEmail());

        if(existing != null){
            System.out.println("Email already registered.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        System.out.println("Registration successful.");
    }

    public void login(String email, String password){
        User user = userRepository.findByEmail(email);

        if(user == null){
            System.out.println("Email not registered.Try Signing Up");
            return;
        }

        if(passwordEncoder.matches(password,user.getPassword())){
            System.out.println("Successfully logged in!");
        }
        else{
            System.out.println("Invalid Password");
        }

    }


}
