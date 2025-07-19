package com.example.f4backend.service;

import com.example.f4backend.entity.Rating;
import com.example.f4backend.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveRating(Rating rating) {
        rating.setCreatedAt(LocalDateTime.now());
        if (rating.getScore() < 1 || rating.getScore() > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }
        return ratingRepository.save(rating);
    }
}
