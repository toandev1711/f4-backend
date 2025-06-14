package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.MessageResponse;
import com.example.f4backend.dto.request.MessageRequest;
import com.example.f4backend.entity.Message;
import com.example.f4backend.entity.Notification;
import com.example.f4backend.service.MessageService;
import lombok.RequiredArgsConstructor;
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
    ) {
        return ApiResponse.<List<Message>>builder()
                .code(1000)
                .message("Success")
                .result(messageService.getChatMessage(userA, userB))
                .build();
    }
}