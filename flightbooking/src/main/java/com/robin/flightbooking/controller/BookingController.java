package com.robin.flightbooking.controller;

import com.robin.flightbooking.dto.requestdto.BookingRequest;
import com.robin.flightbooking.dto.requestdto.CancelRequest;
import com.robin.flightbooking.dto.requestdto.ViewBookingRequest;
import com.robin.flightbooking.entities.Booking;
import com.robin.flightbooking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    public BookingController( BookingService bookingService){this.bookingService = bookingService;}


    @PostMapping("/book")
    public ResponseEntity<String> bookFlight(@Valid @RequestBody BookingRequest bookingRequest){
        return ResponseEntity
                .ok(bookingService.
                        bookFlight(bookingRequest.getFlightId(),bookingRequest.getEmail()));
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelFlight(@Valid @RequestBody CancelRequest cancelRequest){
        return ResponseEntity
                .ok(bookingService.cancelBooking(cancelRequest.getBookingId()));
    }


    @PostMapping("/view")
    public ResponseEntity<?> viewBookings(@Valid @RequestBody ViewBookingRequest viewBookingRequest){
        return ResponseEntity
                .ok(bookingService.getUserBookings(viewBookingRequest.getEmail()));
    }

}
