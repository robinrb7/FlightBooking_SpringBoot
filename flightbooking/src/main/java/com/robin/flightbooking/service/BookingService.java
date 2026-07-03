package com.robin.flightbooking.service;

import com.robin.flightbooking.entities.Booking;
import com.robin.flightbooking.entities.Flight;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.exception.BookingNotFoundException;
import com.robin.flightbooking.exception.FlightNotFoundException;
import com.robin.flightbooking.exception.SeatNotAvailableException;
import com.robin.flightbooking.exception.UserNotFoundException;
import com.robin.flightbooking.repository.BookingRepository;
import com.robin.flightbooking.repository.FlightRepository;
import com.robin.flightbooking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          FlightRepository flightRepository,
                          UserRepository userRepository){
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }



    public String bookFlight(String flightId, User user){
        Flight flight = flightRepository.findByFlightId(flightId);

        if(flight == null){
            throw new FlightNotFoundException("No flight exists with this flight Id");
        }

        if(flight.getAvailableSeats() <= 0){
            throw new SeatNotAvailableException("No seats are available on this flight");
        }


        String seatNum = "S" + flight.getAvailableSeats();
        flight.setAvailableSeats(flight.getAvailableSeats()-1);
        flightRepository.save(flight);

        double gst = 0.18 * flight.getBaseFare();

        Booking booking = new Booking(
                flightId,
                user.getEmail(),
                seatNum,
                LocalDate.now(),
                flight.getBaseFare() + gst);

        bookingRepository.save(booking);

        return "Flight Booked Successfully.";
    }


    public List<Booking> getUserBookings(String email){
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException("No User found registered with this email.");
        }

        return bookingRepository.findBookingByBookingUserEmail(email);
    }


    public String cancelBooking(String bookingId){

        Booking bookingToDelete = bookingRepository.findById(bookingId)
                .orElseThrow(()->
                    new BookingNotFoundException("No Booking was found with this Booking Id."));



        String flightId = bookingToDelete.getFlightId();
        Flight flight = flightRepository.findByFlightId(flightId);

        Integer newSeatCapacity = flight.getAvailableSeats() + 1;
        flight.setAvailableSeats(newSeatCapacity);

        bookingRepository.deleteById(bookingId);
        flightRepository.save(flight);

        return "Your booking has been successfully cancelled";
    }



}
