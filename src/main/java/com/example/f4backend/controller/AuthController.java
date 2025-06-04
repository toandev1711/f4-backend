package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.ApiResponse;
import com.example.f4backend.dto.reponse.AuthResponse;
import com.example.f4backend.dto.reponse.IntrospectResponse;
import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.request.AuthRequest;
import com.example.f4backend.dto.request.IntrospectRequest;
import com.example.f4backend.entity.User;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.AuthService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    final AuthService authenticationService;

    @PostMapping("/user")
    public ApiResponse<AuthResponse> loginUser(@RequestBody AuthRequest request) {
        ApiResponse<AuthResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticationService.authenticate(request));
        apiResponse.setCode(ErrorCode.LOGIN_SUSSCESS.getCode());
        return apiResponse;
    }

    @PostMapping("/driver")
    public ApiResponse<AuthResponse> loginDriver(@RequestBody AuthRequest request) {
            ApiResponse<AuthResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticationService.authenticateDriver(request));
        apiResponse.setCode(ErrorCode.LOGIN_SUSSCESS.getCode());
        return apiResponse;
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> verifyToken(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        ApiResponse<IntrospectResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(authenticationService.introspect(request));
        apiResponse.setCode(ErrorCode.LOGIN_SUSSCESS.getCode());
        return apiResponse;
    }



}
