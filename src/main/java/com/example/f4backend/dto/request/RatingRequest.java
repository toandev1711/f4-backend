package com.example.f4backend.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RatingRequest {
    private String bookingId;
    private double rating;
    private String ratingNote;
}
