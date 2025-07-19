package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.DriverResponse;
import com.example.f4backend.dto.reponse.MessageResponse;
import com.example.f4backend.dto.reponse.PartnerWithMessagesResponse;
import com.example.f4backend.dto.request.MessageRequest;
import com.example.f4backend.entity.Driver;
import com.example.f4backend.entity.Message;
import com.example.f4backend.entity.Notification;
import com.example.f4backend.repository.DriverRepository;
import com.example.f4backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final DriverRepository driverRepository;
    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageRequest request) {
        MessageResponse savedMessage = messageService.save(request);
        messagingTemplate.convertAndSendToUser(
                request.getReceiverId(),
                "/queue/messages",
                new Notification(
                        savedMessage.getSenderId(),
                        savedMessage.getReceiverId(),
                        savedMessage.getContent()
                )
        );
    }

    @GetMapping("/{userA}/{userB}")
    public ApiResponse<List<Message>> getMessagesBetweenUsers(
            @PathVariable String userA,
            @PathVariable String userB
    )
    {
        return ApiResponse.<List<Message>>builder()
                .code(1000)
                .message("Success")
                .result(messageService.getChatMessage(userA, userB))
                .build();
    }

    @GetMapping("/partners/{senderId}")
    public ApiResponse<List<DriverResponse>> getChatPartners(@PathVariable String senderId) {
        List<DriverResponse> partners = messageService.getChatPartners(senderId);
        return ApiResponse.<List<DriverResponse>>builder()
                .code(1000)
                .result(partners)
                .build();
    }

    @GetMapping("/partners-with-messages/{senderId}")
    public List<PartnerWithMessagesResponse> getPartnersWithMessages(@PathVariable String senderId) {
        return messageService.getChatPartnersWithMessages(senderId);
    }
}