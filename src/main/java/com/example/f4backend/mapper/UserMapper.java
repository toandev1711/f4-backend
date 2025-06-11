package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.UserResponse;
import com.example.f4backend.dto.request.UserCreationRequest;
import com.example.f4backend.dto.request.UserUpdateRequest;
import com.example.f4backend.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")

public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    User toUser(UserCreationRequest request);

    @Mapping(source = "locked", target = "isLocked")
    UserResponse toUserResponse(User user);
}