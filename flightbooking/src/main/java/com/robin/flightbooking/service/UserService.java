package com.robin.flightbooking.service;
//services handles all the business logic


import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.EmailAlreadyExistsException;
import com.robin.flightbooking.exception.UserNotFoundException;
import com.robin.flightbooking.exception.InvalidPasswordException;
import com.robin.flightbooking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public String signUp(User user){
        User existing = userRepository.findByEmail(user.getEmail());

        if(existing != null){
            throw new EmailAlreadyExistsException("Email is already registered.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successful";
    }

    public String login(String email, String password){
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserNotFoundException("Email not registered");
        }

        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new InvalidPasswordException("Invalid Password");
        }

        return "Login successfully";
    }


}
