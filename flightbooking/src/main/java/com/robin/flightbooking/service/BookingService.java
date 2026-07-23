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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
            log.warn("Booking attempted for non-existing flight Id: {}", flightId);
            throw new FlightNotFoundException("No flight exists with this flight Id");
        }
        if(flight.getAvailableSeats() <= 0){
            log.warn("Booking Failed.No seats available for flight Id: {}", flightId);
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
            log.warn("Booking attempted with no user details.");
            throw new UserNotFoundException("No user registered with this email");
        }

        //Establish relationships
        booking.setFlight(flight);
        booking.setUser(user);

        bookingRepository.save(booking);
        log.info(
                "Booking created successfully. bookingId={}, userId={}, flightId={}",
                booking.getBookingId(),
                user.getUserId(),
                flight.getFlightId()
        );

        return "Flight Booked Successfully.";
    }

    public List<BookingResponse> getUserBookings(){
        User user = authService.getCurrentUser();

        if(user == null){
            log.warn("Unable to fetch bookings because authenticated user was not found");
            throw new UserNotFoundException("No User found registered with this email.");
        }

        log.info("View User bookings request made by user with email : {}", user.getEmail());
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
        log.info("{} bookings were found of user with email: {}", user.getEmail());

        if(responseList.isEmpty()){
            log.info("No bookings found for userId={}",
                    user.getUserId());
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
            log.warn("Unauthorized booking cancellation attempt. userId={}, bookingId={}, bookingOwnerId={}",
                    currentUser.getEmail(),bookingId, bookingOwnerUser.getEmail());
            throw new UnauthorizedException("This is not your booking to cancel");
        }

        Flight flight = bookingToDelete.getFlight();
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);

        bookingRepository.delete(bookingToDelete);
        log.info("Booking cancelled successfully. bookingId={}, userId={}",
                bookingId, currentUser.getUserId());

        return "Your booking has been successfully cancelled";
    }



}
