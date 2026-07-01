package com.robin.flightbooking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

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



    public Flight(){};

    public Flight(String flightId, String src, String dest,
                  LocalDate date,LocalTime deptTime,int availableSeats, double fare){
        this.flightId = flightId;
        this.source = src;
        this.destination = dest;
        this.date = date;
        this.departureTime = deptTime;
        this.availableSeats = availableSeats;
        this.baseFare = fare;
    }


    public String getFlightId(){
        return flightId;
    }
    public String getSource(){
        return source;
    }
    public String getDestination(){
        return destination;
    }
    public LocalDate getDate(){
        return date;
    }
    public LocalTime getTime(){
        return departureTime;
    }
    public Integer getAvailableSeats(){
        return availableSeats;
    }
    public double getBaseFare(){
        return baseFare;
    }

    public void setAvailableSeats(Integer availableSeats){
        this.availableSeats = availableSeats;
    }

}
