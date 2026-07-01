package com.robin.flightbooking.dto;

import java.time.LocalDate;
import java.util.Date;

public class SearchRequest {
    private String source;
    private String destination;
    private LocalDate date;

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }
}
