package com.robin.flightbooking.service;

import com.robin.flightbooking.entities.Booking;
import com.robin.flightbooking.entities.Flight;
import com.robin.flightbooking.entities.User;
import com.robin.flightbooking.repository.BookingRepository;
import com.robin.flightbooking.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository){
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }



    public void bookFlight(String flightId, User user){
        Flight flight = flightRepository.findByFlightId(flightId);

        if(flight == null){
            System.out.println("Enter valid Flight ID!");
            return;
        }

        if(flight.getAvailableSeats() <= 0){
            System.out.println("No seat available! Try another flight.");
            return;
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
    }

    public Flight findFlightById(String flightId){
        return flightRepository.findByFlightId(flightId);
    }

    public List<Booking> getUserBookings(String email){
        return bookingRepository.findBookingByBookingUserEmail(email);
    }


    public void cancelBooking(String bookingId){

        Booking bookingToDelete = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found!"));

        String flightId = bookingToDelete.getFlightId();
        Flight flight = findFlightById(flightId);

        Integer newSeatCapacity = flight.getAvailableSeats() + 1;
        flight.setAvailableSeats(newSeatCapacity);

        bookingRepository.deleteById(bookingId);
        flightRepository.save(flight);
        System.out.println("Your booking has been successfully cancelled.\n");
    }



}
