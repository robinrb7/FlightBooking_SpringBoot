package com.robin.flightbooking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="flights")
public class Flight {
    @Id
    @Column(name="flight_id",length=20)
    private String flightId;

    @Column(name="source_from",length=20)
    private String source;

    @Column(name="destination_to",length=20)
    private String destination;

    @Column(name="departure_time")
    private LocalTime departureTime;

    @Column(name="flight_date")
    private LocalDate date;

    @Column(name="available_seats")
    private Integer availableSeats;

    @Column(name="base_fare")
    private double baseFare;

    @OneToMany(mappedBy = "flight")
    @JsonIgnore
    private List<Booking> bookings;


    public Flight(String flightId, String source, String destination, LocalTime departureTime, LocalDate date, Integer availableSeats, double baseFare) {
        this.flightId=flightId;
        this.source=source;
        this.destination=destination;
        this.departureTime=departureTime;
        this.availableSeats=availableSeats;
        this.date=date;
        this.baseFare=baseFare;
    }
}
