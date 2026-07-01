package com.robin.flightbooking.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="bookingId",length=40)
    private String bookingId;

    @Column(name="flightId",length=20)
    private String flightId;

    @Column(name="user_email",length=40)
    private String bookingUserEmail;

    @Column(name="seat_number",length=10)
    private String seatNumber;

    @Column(name="booking_date")
    private LocalDate bookingDate;

    @Column(name="total_amount")
    private double totalAmount;

    public Booking(){};

    public Booking(String flightId, String email, String seatNumber,
                   LocalDate bookingDate, double totalAmount){

        this.flightId = flightId;
        this.bookingUserEmail = email;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
    }

    public String getBookingId(){
        return bookingId;
    }
    public String getBookingUserEmail(){
        return bookingUserEmail;
    }
    public String getFlightId(){
        return flightId;
    }
    public String getSeatNumber(){
        return seatNumber;
    }
    public LocalDate getBookingDate(){
        return bookingDate;
    }
    public double getBookingAmount(){
        return totalAmount;
    }
}

