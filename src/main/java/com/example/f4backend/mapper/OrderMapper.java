package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.CoordinatesResponse;
import com.example.f4backend.dto.reponse.DeliveryDetailResponse;
import com.example.f4backend.dto.reponse.OrderResponse;
import com.example.f4backend.dto.request.CoordinatesRequest;
import com.example.f4backend.dto.request.DeliveryDetailRequest;
import com.example.f4backend.dto.request.OrderRequest;
import com.example.f4backend.entity.Coordinates;
import com.example.f4backend.entity.DeliveryDetail;
import com.example.f4backend.entity.Order;

import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    @Mapping(target = "orderId", ignore = true)
    Order toOrder(OrderRequest request);


    @Mapping(source = "orderStatus.statusName", target = "statusName")
    OrderResponse toOrderResponse(Order order);

    //ORDER DETAIL MAPPER

    @Mapping(target = "deliveryDetailId", ignore = true)
    @Mapping(target = "vehicleType", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "order.orderId", source = "orderId")
    DeliveryDetail toDeliveryDetail(DeliveryDetailRequest request);

    @Mapping(target = "vehicleTypeName", source = "vehicleType.vehicleTypeName")
    @Mapping(target = "orderId", source = "order.orderId")
    DeliveryDetailResponse toDeliveryDetailResponse(DeliveryDetail deliveryDetail);

    //COORDINATES
    @Mapping(target = "coordinateId", ignore = true)
    @Mapping(target = "deliveryDetail", ignore = true)
    Coordinates toCoordinates(CoordinatesRequest request);

    @Mapping(target = "deliveryDetailId", source = "deliveryDetail.deliveryDetailId")
    CoordinatesResponse toCoordinatesResponse(Coordinates coordinates);
}

