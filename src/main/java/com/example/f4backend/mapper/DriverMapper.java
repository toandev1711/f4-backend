package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.*;
import com.example.f4backend.dto.request.*;
import com.example.f4backend.entity.*;
import com.example.f4backend.enums.DriverStatus;
import com.example.f4backend.enums.DriverType;
import com.example.f4backend.enums.Role;
import org.mapstruct.*;
import java.sql.Date;

import java.time.LocalDate;
import java.util.Collections;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DriverMapper {

    // Request to Entity
    // @Mapping(target = "id", ignore = true)
    // @Mapping(target = "createdDate", expression =
    // "java(java.time.LocalDate.now())")
    // @Mapping(target = "roles", expression =
    // "java(java.util.Collections.singleton(com.example.f4backend.enums.Role.DRIVER))")
    // User toUser(UserCreationRequest request);

    @Mapping(target = "driverId", ignore = true)
    @Mapping(target = "driverType", qualifiedByName = "mapDriverType")
    @Mapping(target = "driverStatus", qualifiedByName = "mapDriverStatus")
    Driver toDriver(DriverRequest request);

    @Mapping(target = "identifierId", ignore = true)
    @Mapping(target = "createAt", expression = "java(java.sql.Date.valueOf(java.time.LocalDate.now()))")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "driver", source = "driver")
    IdentifierCard toIdentifierCard(IdentifierCardRequest request, Driver driver, DocumentStatus status);

    @Mapping(target = "vehicleId", ignore = true)
    @Mapping(target = "createAt", expression = "java(java.sql.Date.valueOf(java.time.LocalDate.now()))")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "driver", source = "driver")
    VehicleDetail toVehicleDetail(VehicleDetailRequest request, Driver driver, DocumentStatus status);

    @Mapping(target = "licenseCarId", ignore = true)
    @Mapping(target = "createAt", expression = "java(java.sql.Date.valueOf(java.time.LocalDate.now()))")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "driver", source = "driver")
    LicenseCar toLicenseCar(LicenseCarRequest request, Driver driver, DocumentStatus status);

    // Entity to Response
    // UserResponse toUserResponse(User user);

    @Mapping(target = "userId", source = "user.id")
    DriverResponse toDriverResponse(Driver driver);

    @Mapping(target = "driverId", source = "driver.driverId")
    @Mapping(target = "statusName", source = "status.statusName")
    IdentifierCardResponse toIdentifierCardResponse(IdentifierCard identifierCard);

    @Mapping(target = "driverId", source = "driver.driverId")
    @Mapping(target = "statusName", source = "status.statusName")
    LicenseCarResponse toLicenseCarResponse(LicenseCar licenseCar);

    @Mapping(target = "driverId", source = "driver.driverId")
    @Mapping(target = "statusName", source = "status.statusName")
    @Mapping(target = "vehicleTypeName", source = "vehicleType.vehicleTypeName")
    VehicleDetailResponse toVehicleDetailResponse(VehicleDetail vehicleDetail);

    // Helper methods
    @Named("mapDriverType")
    default DriverType mapDriverType(String type) {
        return DriverType.valueOf(type.toUpperCase());
    }

    @Named("mapDriverStatus")
    default DriverStatus mapDriverStatus(String status) {
        return DriverStatus.valueOf(status.toUpperCase());
    }

    // driver update
//    User updateUserFromDto(DriverInfoRequest dto, @MappingTarget User entity);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createAt", expression = "java(java.sql.Date.valueOf(java.time.LocalDate.now()))")
    void updateIdentifierCardFromDto(IdentifierCardRequest request, @MappingTarget IdentifierCard IdentifierCard);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createAt", expression = "java(java.sql.Date.valueOf(java.time.LocalDate.now()))")
    void updateLicenseCarFromDto(LicenseCarRequest request, @MappingTarget LicenseCar licenseCar);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createAt", expression = "java(java.sql.Date.valueOf(java.time.LocalDate.now()))")
    void updateVehicleDetailFromDto(VehicleDetailRequest request, @MappingTarget VehicleDetail vehicleDetail);
}