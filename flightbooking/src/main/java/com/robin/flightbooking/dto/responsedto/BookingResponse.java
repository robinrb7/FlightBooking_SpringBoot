package com.robin.flightbooking.dto.responsedto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private String bookingId;
    private String email;
    private String FullName;

    private String flightId;
    private String source;
    private String destination;

    private String seatNumber;
    private LocalDate bookingDate;
    private double totalAmount;



}
