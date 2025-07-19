package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.TodayRevenue;
import com.example.f4backend.entity.Driver;
import com.example.f4backend.entity.DriverRevenue;
import com.example.f4backend.repository.DriverRepository;
import com.example.f4backend.repository.DriverRevenueRepository;
import com.twilio.rest.api.v2010.account.usage.record.Today;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DriverRevenueService {
    private final DriverRevenueRepository driverRevenueRepository;
    private final DriverRepository driverRepository;
    public void addRevenueForBooking(String driverId, String bookingId, LocalDate date, BigDecimal amount) {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        boolean exists = driverRevenueRepository.existsByBookingId(bookingId);
        if (exists) {
            throw new RuntimeException("Revenue for this booking has already been recorded.");
        }
        DriverRevenue revenue = new DriverRevenue();
        revenue.setDriver(driver);
        revenue.setBookingId(bookingId);
        revenue.setDate(date);
        revenue.setAmount(amount);
        driverRevenueRepository.save(revenue);
    }

    public List<TodayRevenue> getTodayRevenueByDriver(String driverId) {
        LocalDate today = LocalDate.now();
        List<TodayRevenue> responseList = new ArrayList<>();
        List<DriverRevenue> list = driverRevenueRepository.findAllByDriverDriverIdAndDate(driverId, today);
        for(DriverRevenue driverRevenue : list){
            responseList.add(TodayRevenue.builder()
                            .bookingId(driverRevenue.getBookingId())
                            .price(driverRevenue.getAmount())
                            .build());
        }

        return responseList;
    }

    public BigDecimal getTotalRevenueByDriverId(String driverId) {
        return driverRevenueRepository.getTotalAmountByDriverId(driverId);
    }
}
