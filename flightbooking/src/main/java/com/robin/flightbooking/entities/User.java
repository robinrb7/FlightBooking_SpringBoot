package com.robin.flightbooking.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor



@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    @Column(name="first_name", length = 20)
    private String firstName;

    @Column(name="last_name", length = 20)
    private String lastName;

    @Column(name="email", length = 40, unique = true)
    private String email;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "password_login", length = 100)
    private String password;


    public User(String fName, String lName, String email, String phoneNum, String password)
    {
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.phoneNumber = phoneNum;
        this.password = password;
    }



}
