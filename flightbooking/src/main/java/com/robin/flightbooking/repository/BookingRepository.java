package com.robin.flightbooking.repository;

import com.robin.flightbooking.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,String> {
    List<Booking> findBookingByBookingUserEmail(String bookingUserEmail);
}
