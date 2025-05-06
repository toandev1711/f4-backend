package com.example.f4backend.dto.reponse;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String profilePicture;
    private LocalDate dob;
    private LocalDate createdDate;
    private boolean isLocked;
}