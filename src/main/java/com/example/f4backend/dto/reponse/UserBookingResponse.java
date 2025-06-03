package com.example.f4backend.dto.reponse;

import com.example.f4backend.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBookingResponse {
    private String message;
    private Booking booking;
}
