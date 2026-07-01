package com.robin.flightbooking.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
    @Column(name="first_name", length = 20)
    private String firstName;

    @Column(name="last_name", length = 20)
    private String lastName;

    @Id
    @Column(name="email", length = 40)
    private String email;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "password_login", length = 30)
    private String password;

    public User(){};

    public User(String fName, String lName, String email, String phoneNum, String password)
    {
        this.firstName = fName;
        this.lastName = lName;
        this.email = email;
        this.phoneNumber = phoneNum;
        this.password = password;
    }



    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
}
