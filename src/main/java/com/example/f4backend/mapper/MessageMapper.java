package com.example.f4backend.mapper;

import com.example.f4backend.dto.request.MessageRequest;
import com.example.f4backend.entity.Message;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {
    Message toMessage(MessageRequest request);
}
