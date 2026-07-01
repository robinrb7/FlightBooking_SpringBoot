package com.robin.flightbooking.controller;

import com.robin.flightbooking.dto.BookingRequest;
import com.robin.flightbooking.dto.CancelRequest;
import com.robin.flightbooking.dto.ViewBookingRequest;
import com.robin.flightbooking.entities.Booking;
import com.robin.flightbooking.service.BookingService;
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
    public void bookFlight(@RequestBody BookingRequest bookingRequest){
        bookingService.bookFlight(bookingRequest.getFlightId(),bookingRequest.getUser());
    }

    @PostMapping("/cancel")
    public void cancelFlight(@RequestBody CancelRequest cancelRequest){
        bookingService.cancelBooking(cancelRequest.getBookingId());
    }


    @PostMapping("/view")
    public List<Booking> viewBookings(@RequestBody ViewBookingRequest viewBookingRequest){
        return bookingService.getUserBookings(viewBookingRequest.getEmail());
    }

}
