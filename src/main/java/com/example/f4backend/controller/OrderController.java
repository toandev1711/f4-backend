package com.example.f4backend.controller;

import com.example.f4backend.dto.reponse.*;
import com.example.f4backend.dto.request.DeliveryDetailRequest;
import com.example.f4backend.dto.request.OrderRequest;
import com.example.f4backend.enums.ErrorCode;
import com.example.f4backend.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request){
        return ApiResponse.<OrderResponse>
                        builder()
                .code(ErrorCode.CREATE_ORDER_SUCCESS.getCode())
                .result(orderService.createOrder(request))
                .message(ErrorCode.CREATE_ORDER_SUCCESS.getMessage())
                .build();
    }

    @PostMapping("/create-detail")
    public ApiResponse<DeliveryDetailResponse> createDetail(@RequestBody DeliveryDetailRequest request){
        return ApiResponse.<DeliveryDetailResponse>
                        builder()
                .code(ErrorCode.CREATE_ORDERDETAIL_SUCCESS.getCode())
                .result(orderService.createDeliveryDetail(request))
                .message(ErrorCode.CREATE_ORDERDETAIL_SUCCESS.getMessage())
                .build();
    }
}