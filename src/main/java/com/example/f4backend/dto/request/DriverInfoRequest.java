package com.example.f4backend.dto.request;

import lombok.Data;

@Data
public class DriverInfoRequest {
    private String password;
    private String fullName;
    private String email;
    private String phone;
}
