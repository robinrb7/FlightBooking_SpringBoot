package com.robin.flightbooking.service;

import com.robin.flightbooking.dto.responsedto.BookingResponse;
import com.robin.flightbooking.entities.Booking;
import com.robin.flightbooking.entities.Flight;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.*;
import com.robin.flightbooking.repository.BookingRepository;
import com.robin.flightbooking.repository.FlightRepository;
import com.robin.flightbooking.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final AuthService authService;



    @Transactional
    public String bookFlight(String flightId){
        Flight flight = flightRepository.findByFlightId(flightId);
        if(flight == null){
            throw new FlightNotFoundException("No flight exists with this flight Id");
        }
        if(flight.getAvailableSeats() <= 0){
            throw new SeatNotAvailableException("No seats are available on this flight");
        }


        String seatNum = "S" + flight.getAvailableSeats();
        flight.setAvailableSeats(flight.getAvailableSeats()-1);


        double gst = 0.18 * flight.getBaseFare();

        Booking booking = new Booking(
                seatNum,
                LocalDate.now(),
                flight.getBaseFare() + gst);


        User user = authService.getCurrentUser();
        if(user==null){
            throw new UserNotFoundException("No user registered with this email");
        }

        //Establish relationships
        booking.setFlight(flight);
        booking.setUser(user);

        bookingRepository.save(booking);
        return "Flight Booked Successfully.";
    }

    public List<BookingResponse> getUserBookings(){
        User user = authService.getCurrentUser();

        if(user == null){
            throw new UserNotFoundException("No User found registered with this email.");
        }

        List<Booking> bookingList =  bookingRepository.findByUser(user);
        List<BookingResponse> responseList = new ArrayList<>();

        for(Booking booking: bookingList){
            BookingResponse response = new BookingResponse(
                    booking.getBookingId(),
                    booking.getUser().getEmail(),
                    booking.getUser().getFirstName()+ " " + booking.getUser().getLastName(),
                    booking.getFlight().getFlightId(),
                    booking.getFlight().getSource(),
                    booking.getFlight().getDestination(),
                    booking.getSeatNumber(),
                    booking.getBookingDate(),
                    booking.getTotalAmount()
            );

            responseList.add(response);
        }

        return responseList;
    }

    @Transactional
    public String cancelBooking(String bookingId){

        Booking bookingToDelete = bookingRepository.findById(bookingId)
                .orElseThrow(()->
                    new BookingNotFoundException("No Booking was found with this Booking Id."));

        User bookingOwnerUser = bookingToDelete.getUser();
        User currentUser = authService.getCurrentUser();


        if(!bookingOwnerUser.getUserId().equals(currentUser.getUserId())){
            throw new UnauthorizedException("This is not your booking to cancel");
        }

        Flight flight = bookingToDelete.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);

        bookingRepository.delete(bookingToDelete);
        return "Your booking has been successfully cancelled";
    }



}
