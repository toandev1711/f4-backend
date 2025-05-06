package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.request.UserCreationRequest;
import com.example.f4backend.entity.User;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    @PostMapping
    public ApiResponse<UserResponse> create(@RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>
                builder()
                .code(ErrorCode.CREATE_USER_SUCCESS.getCode())
                .result(userService.createUser(request))
                .message(ErrorCode.CREATE_USER_SUCCESS.getMessage())
                .build();
    }
}
