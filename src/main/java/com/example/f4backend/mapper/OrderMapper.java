package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.DeliveryDetailResponse;
import com.example.f4backend.dto.reponse.OrderResponse;
import com.example.f4backend.dto.request.DeliveryDetailRequest;
import com.example.f4backend.dto.request.OrderRequest;
import com.example.f4backend.entity.DeliveryDetail;
import com.example.f4backend.entity.Order;
import com.example.f4backend.enums.DriverType;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    @Mapping(target = "orderId", ignore = true)
    Order toOrder(OrderRequest request);

    @Named("mapDriverType")
    default DriverType mapDriverType(String type) {
        return DriverType.valueOf(type.toUpperCase());
    }

    @Mapping(source = "orderStatus.statusName", target = "statusName")
    OrderResponse toOrderResponse(Order order);

    //ORDER DETAIL MAPPER

    @Mapping(target = "deliveryDetailId", ignore = true)
    DeliveryDetail toDeliveryDetail(DeliveryDetailRequest request);

    @Mapping(target = "vehicleTypeName", source = "vehicleType.vehicleTypeName")
    DeliveryDetailResponse toDeliveryDetailResponse(DeliveryDetail deliveryDetail);
}

