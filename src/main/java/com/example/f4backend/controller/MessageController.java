package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.MessageResponse;
import com.example.f4backend.dto.request.MessageRequest;
import com.example.f4backend.entity.Message;
import com.example.f4backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ApiResponse<MessageResponse> sendMessage(@RequestBody MessageRequest request) {
        return ApiResponse.<MessageResponse>builder()
                .code(1000)
                .message("Message was send")
                .result(messageService.save(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<Message>> getMessagesBetweenUsers(
            @RequestParam String userA,
            @RequestParam String userB
    ) {
        return ApiResponse.<List<Message>>builder()
                .code(1000)
                .result(messageService.getChatMessage(userA, userB))
                .build();
    }
}
