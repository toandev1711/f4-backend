package com.example.f4backend.dto.request;
import lombok.AccessLevel;
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
public class UserUpdateRequest {
    String password;
    String fullName;
    String email;
    String phone;
    String address;
    String profilePicture;
    LocalDate dob;
    Boolean isLocked;
    String userNumber;
}
