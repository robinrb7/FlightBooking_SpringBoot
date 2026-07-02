package com.robin.flightbooking.entities;


import jakarta.persistence.*;

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

    @Column(name="email", length = 40)
    private String email;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "password_login", length = 100)
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



    public String getUserId(){return userId;}
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

    public void setPassword(String pass){
        this.password = pass;
    }
}
