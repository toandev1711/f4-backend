package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.request.UserCreationRequest;
import com.example.f4backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
}
