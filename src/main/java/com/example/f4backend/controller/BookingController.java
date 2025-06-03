package com.example.f4backend.controller;


import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.UserBookingResponse;
import com.example.f4backend.dto.request.BookingAction;
import com.example.f4backend.dto.request.BookingRequest;
import com.example.f4backend.entity.Booking;
import com.example.f4backend.entity.Message;
import com.example.f4backend.repository.BookingRepository;
import com.example.f4backend.service.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private final BookingService bookingService;
    private final SimpMessagingTemplate messagingTemplate;
    @PostMapping("/create")
    public ApiResponse<UserBookingResponse> createBooking(@RequestBody BookingRequest request) throws JsonProcessingException {
        String bookingId = bookingService.createBooking(request);
        request.setBookingId(bookingId);
        messagingTemplate.convertAndSend("/topic/drivers", request);
        UserBookingResponse userBookingResponse = new UserBookingResponse();
        userBookingResponse.setMessage("Booking was send to driver");
        return ApiResponse.<UserBookingResponse>builder()
                .code(1000)
                .result(userBookingResponse)
                .build();
    }

    @MessageMapping("/accept")
    public void acceptBooking(BookingAction bookingAction){
        boolean accepted = bookingService.acceptBooking(bookingAction.getBookingId(), bookingAction.getDriverId());
        if (accepted) {
            Optional<Booking> optionalBooking = bookingRepository.findById(bookingAction.getBookingId());
            Booking booking = optionalBooking.get();
            messagingTemplate.convertAndSendToUser(
                    booking.getUser().getId(),
                    "/queue/booking-status",
                    booking.getDriver().getDriverId() + " đã chấp nhận yêu cầu của bạn."
            );
        }
    }
}