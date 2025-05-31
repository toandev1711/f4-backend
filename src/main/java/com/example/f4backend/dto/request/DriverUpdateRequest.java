package com.example.f4backend.dto.request;

import java.time.LocalDate;
import lombok.Data;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverUpdateRequest {
    String password;
    String fullName;
    String email;
    String phone;
    String address;
    String profilePicture;
    LocalDate dob;
}
