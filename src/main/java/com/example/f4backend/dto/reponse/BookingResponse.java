package com.example.f4backend.dto.reponse;

import com.example.f4backend.entity.Driver;
import com.example.f4backend.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private String user;
    private String driver;
    private LocalDateTime createdDate;
    private String pickupAddress;
    private String dropoffAddress;
    private Double price;
    private double rating;
}
