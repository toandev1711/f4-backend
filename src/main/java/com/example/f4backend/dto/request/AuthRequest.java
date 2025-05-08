package com.example.f4backend.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthRequest {
    String phone;
    String password;
}
