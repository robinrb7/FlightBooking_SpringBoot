package com.robin.flightbooking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 20, message = "First name cannot exceed 20 letters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 20, message = "Last name cannot exceed 20 letters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 40, message = "Email cannot exceed 20 letters")
    private String email;

    @NotBlank(message = "Phone Number is required")
    @Pattern(
            regexp = "^[6-9][0-9]{9}$",
            message = "Phone number must contain 10 digits and must start with 6-9."
    )
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])[A-Za-z0-9@#$%]{6,20}$",
            message = "Password must be between 6 and 20 characters and contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;



}
