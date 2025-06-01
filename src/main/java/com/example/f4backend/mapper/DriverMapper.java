package com.example.f4backend.mapper;

import com.example.f4backend.dto.reponse.*;
import com.example.f4backend.dto.request.*;
import com.example.f4backend.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DriverMapper {

    @Mapping(target = "driverId", ignore = true)
    @Mapping(target = "driverStatus", source = "driverStatus")
    @Mapping(target = "driverType", source = "driverType")
    @Mapping(target = "createDate", expression = "java(java.sql.Date.valueOf(java.time.LocalDate.now()))")
    Driver toDriver(DriverRequest request, DriverStatus driverStatus, DriverType driverType);

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

    // To Response
    // @Mapping(target = "userId", source = "user.id")
    // DriverResponse toDriverResponse(Driver driver);

    // Entity to Response
    @Mapping(target = "driverTypeName", source = "driverType.driverTypeName")
    @Mapping(target = "driverStatusName", source = "driverStatus.driverStatusName")
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

    @Mapping(target = "vehicleTypeId", source = "vehicleTypeId")
    @Mapping(target = "vehicleTypeName", source = "vehicleTypeName")
    VehicleTypeResponse VehicleTypeResponse(VehicleType vehicleType);

    @Mapping(target = "driverTypeId", source = "driverTypeId")
    @Mapping(target = "driverTypeName", source = "driverTypeName")
    DriverTypeResponse toDriverTypeResponse(com.example.f4backend.entity.DriverType driverType);

    @Mapping(target = "driverStatusId", source = "driverStatusId")
    @Mapping(target = "driverStatusName", source = "driverStatusName")
    DriverStatusResponse toDriverStatusResponse(com.example.f4backend.entity.DriverStatus driverStatus);

    // driver update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDriver(@MappingTarget Driver driver, DriverUpdateRequest request);

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