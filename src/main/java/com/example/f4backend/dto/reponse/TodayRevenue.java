package com.example.f4backend.dto.reponse;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TodayRevenue {
    String bookingId;
    BigDecimal price;
}
