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
import java.util.Objects;

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

        if (request.getPickupAddress() == null || request.getDropoffAddress() == null || request.getPrice() == null) {
            throw new IllegalArgumentException("Missing required fields");
        }

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

//    @Transactional
//    public DeliveryDetailResponse createDeliveryDetail(DeliveryDetailRequest request) {
//        try {
//            log.info("Starting createDeliveryDetail for orderId: {}", request.getOrderId());
//
//            // Kiểm tra đầu vào
//            if (request.getPickupAddress() == null || request.getDropoffAddress() == null || request.getPrice() == null) {
//                log.error("Missing required fields in request: pickupAddress={}, dropoffAddress={}, price={}",
//                        request.getPickupAddress(), request.getDropoffAddress(), request.getPrice());
//                throw new IllegalArgumentException("Missing required fields");
//            }
//            log.info("Input validation passed");
//
//            // Tìm Order
//            log.info("Fetching Order with ID: {}", request.getOrderId());
//            Order order = orderRepository.findById(request.getOrderId())
//                    .orElseThrow(() -> {
//                        log.error("Order not found with ID: {}", request.getOrderId());
//                        return new EntityNotFoundException("Order not found with ID: " + request.getOrderId());
//                    });
//            log.info("Order found: {}", order.getOrderId());
//
//            // Tìm VehicleType
//            log.info("Fetching VehicleType with ID: {}", request.getVehicleTypeId());
//            VehicleType vehicleType = vehicleTypeRepository.findById(request.getVehicleTypeId())
//                    .orElseThrow(() -> {
//                        log.error("VehicleType not found with ID: {}", request.getVehicleTypeId());
//                        return new RuntimeException("Vehicle not found");
//                    });
//            log.info("VehicleType found: {}", vehicleType.getVehicleTypeName());
//
//            // Ánh xạ DeliveryDetail
//            log.info("Mapping DeliveryDetailRequest to DeliveryDetail");
//            DeliveryDetail deliveryDetail = orderMapper.toDeliveryDetail(request);
//            deliveryDetail.setOrder(order);
//            deliveryDetail.setVehicleType(vehicleType);
//            log.info("DeliveryDetail mapped and set with orderId: {}, vehicleTypeId: {}",
//                    deliveryDetail.getOrder().getOrderId(), deliveryDetail.getVehicleType().getVehicleTypeId());
//
//            // Lưu DeliveryDetail
//            log.info("Saving DeliveryDetail to database");
//            DeliveryDetail savedDetail = deliveryDetailRepository.save(deliveryDetail);
//            log.info("DeliveryDetail saved with ID: {}", savedDetail.getDeliveryDetailId());
//
//            // Ánh xạ sang Response
//            log.info("Mapping saved DeliveryDetail to DeliveryDetailResponse");
//            DeliveryDetailResponse response = orderMapper.toDeliveryDetailResponse(savedDetail);
//            log.info("Response created successfully for DeliveryDetail ID: {}", savedDetail.getDeliveryDetailId());
//
//            return response;
//        } catch (Exception e) {
//            log.error("Error in createDeliveryDetail at orderId: {}. Error message: {}. Stack trace: {}",
//                    request.getOrderId(), e.getMessage(), e.getStackTrace());
//            throw new RuntimeException("Failed to create delivery detail: " + e.getMessage(), e);
//        }
//    }
}