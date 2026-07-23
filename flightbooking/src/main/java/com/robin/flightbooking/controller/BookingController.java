package com.robin.flightbooking.controller;

import com.robin.flightbooking.dto.requestdto.BookingRequest;
import com.robin.flightbooking.dto.requestdto.CancelRequest;
import com.robin.flightbooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<String> bookFlight(@Valid @RequestBody BookingRequest bookingRequest){
        log.info("Booking request received for flight Id: {}", bookingRequest.getFlightId());
        return ResponseEntity
                .ok(bookingService.
                        bookFlight(bookingRequest.getFlightId()));
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelFlight(@Valid @RequestBody CancelRequest cancelRequest){
        log.info("Cancel Booking request received for booking Id: {}", cancelRequest.getBookingId());
        return ResponseEntity
                .ok(bookingService.cancelBooking(cancelRequest.getBookingId()));
    }


    @PostMapping("/view")
    public ResponseEntity<?> viewBookings(){
        log.info("View bookings request received");
        return ResponseEntity
                .ok(bookingService.getUserBookings());
    }

}
