package com.robin.flightbooking.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;



@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="bookingId",length=40)
    private String bookingId;

    @Column(name="seat_number",length=10)
    private String seatNumber;

    @Column(name="booking_date")
    private LocalDate bookingDate;

    @Column(name="total_amount")
    private double totalAmount;


    public Booking( String seatNumber,
                   LocalDate bookingDate, double totalAmount){

        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;


}

