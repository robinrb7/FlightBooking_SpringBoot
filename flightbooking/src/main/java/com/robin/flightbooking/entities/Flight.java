package com.robin.flightbooking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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



}
