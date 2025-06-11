package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.reponse.WithDrawResponse;
import com.example.f4backend.dto.request.UserCreationRequest;
import com.example.f4backend.dto.request.UserUpdateRequest;
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

import java.util.List;

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

    @PutMapping("/{userId}")
    public ApiResponse<UserResponse> update (@PathVariable String userId, @RequestBody UserUpdateRequest request ){
        return ApiResponse.<UserResponse>
                        builder()
                .code(1000)
                .result(userService.updateUser(userId, request))
                .message("Update successfully")
                .build();
    }

    @GetMapping("/info")
    public ApiResponse<UserResponse> myInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.myInfo())
                .message("Your information")
                .build();
    }

    @GetMapping("/getList")
    public ApiResponse<List<UserResponse>> getListUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(ErrorCode.GET_USER_SUCCESS.getCode())
                .result(userService.getListUser())
                .message(ErrorCode.GET_USER_SUCCESS.getMessage())
                .build();
    }
    @PutMapping("/lock/{userId}")
    public ApiResponse<UserResponse> updateLockStatus(@PathVariable String userId) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.updateLockStatusUser(userId))
                .message("User account locked or unlocked successfully")
                .build();
    }

}
