package com.example.f4backend.service;

import com.example.f4backend.dto.reponse.DeliveryDetailResponse;
import com.example.f4backend.dto.reponse.OrderResponse;
import com.example.f4backend.dto.request.DeliveryDetailRequest;
import com.example.f4backend.dto.request.OrderRequest;
import com.example.f4backend.entity.DeliveryDetail;
import com.example.f4backend.entity.Order;
import com.example.f4backend.entity.OrderStatus;
import com.example.f4backend.entity.VehicleType;
import com.example.f4backend.mapper.OrderMapper;
import com.example.f4backend.repository.DeliveryDetailRepository;
import com.example.f4backend.repository.OrderRepository;
import com.example.f4backend.repository.OrderStatusRepository;
import com.example.f4backend.repository.VehicleTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderStatusRepository orderStatusRepository;
    OrderMapper orderMapper;
    DeliveryDetailRepository  deliveryDetailRepository;
    VehicleTypeRepository vehicleTypeRepository;

    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order with request: {}", request);
        //tìm orderId , vehycle type name từ request,
        // Tìm OrderStatus theo statusId
        OrderStatus orderStatus = orderStatusRepository.findById(request.getStatusId())
                .orElseThrow(() -> {
                    log.error("OrderStatus not found with ID: {}", request.getStatusId());
                    return new EntityNotFoundException("OrderStatus not found with ID: " + request.getStatusId());
                });

        // Ánh xạ OrderRequest thành Order
        Order order = orderMapper.toOrder(request);
        order.setOrderStatus(orderStatus);

        // Gán creationDatetime
        order.setCreationDatetime(new Date(System.currentTimeMillis()));

        // Lưu Order và trả về OrderResponse
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {}", savedOrder.getOrderId());
        return orderMapper.toOrderResponse(savedOrder);
    }

    @Transactional
    public DeliveryDetailResponse createDeliveryDetail(DeliveryDetailRequest request) {
        log.info("Creating delivery detail for orderId: {} with request: {}",request.getOrderId(), request);

        // Tìm Order theo orderId
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> {
                    log.error("Order not found with ID: {}", request.getOrderId());
                    return new EntityNotFoundException("Order not found with ID: " + request.getOrderId());
                });

        VehicleType vehicleType = vehicleTypeRepository.findById(request.getVehicleTypeId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Ánh xạ DeliveryDetailRequest thành DeliveryDetail
        DeliveryDetail deliveryDetail = orderMapper.toDeliveryDetail(request);
        deliveryDetail.setOrder(order);
        deliveryDetail.setVehicleType(vehicleType);

        DeliveryDetail savedDetail = deliveryDetailRepository.save(deliveryDetail);
        log.info("Delivery detail created successfully with ID: {}", savedDetail.getDeliveryDetailId());

        return orderMapper.toDeliveryDetailResponse(savedDetail);
    }
}