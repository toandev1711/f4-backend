//package com.example.f4backend.mapper;
//
//import com.example.f4backend.dto.request.DriverRegistrationRequest;
//import com.example.f4backend.dto.request.IdentifierCardRequest;
//import com.example.f4backend.dto.request.LicenseCarRequest;
//import com.example.f4backend.dto.request.VehicleDetailRequest;
//import com.example.f4backend.entity.*;
//import com.example.f4backend.repository.DocumentStatusRepository;
//import org.mapstruct.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public abstract class DriverMapper {
//    @Autowired
//    private DocumentStatusRepository documentStatusRepository;
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdDate", expression = "java(LocalDate.now())")
//    @Mapping(target = "roles", expression = "java(Collections.singleton(Role.DRIVER))")
//    public abstract User toUser(DriverRegistrationRequest request);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "user", source = "user")
//    @Mapping(target = "driverType", expression = "java(DriverType.valueOf(request.getDriverType().toUpperCase()))")
//    @Mapping(target = "driverStatus", expression = "java(DriverStatus.valueOf(request.getDriverStatus().toUpperCase()))")
//    public abstract Driver toDriver(DriverRegistrationRequest request, User user);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createAt", expression = "java(LocalDate.now())")
//    @Mapping(target = "status", expression = "java(documentStatusRepository.findById(request.getStatusId()).orElseThrow(() -> new RuntimeException(\"Status not found\")))")
//    @Mapping(target = "driver", source = "driver")
//    public abstract IdentifierCard toIdentifierCard(IdentifierCardRequest request, Driver driver);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createAt", expression = "java(LocalDate.now())")
//    @Mapping(target = "status", expression = "java(documentStatusRepository.findById(request.getStatusId()).orElseThrow(() -> new RuntimeException(\"Status not found\")))")
//    @Mapping(target = "driver", source = "driver")
//    public abstract VehicleDetail toVehicleDetail(VehicleDetailRequest request, Driver driver);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createAt", expression = "java(LocalDate.now())")
//    @Mapping(target = "status", expression = "java(documentStatusRepository.findById(request.getStatusId()).orElseThrow(() -> new RuntimeException(\"Status not found\")))")
//    @Mapping(target = "driver", source = "driver")
//    public abstract LicenseCar toLicenseCar(LicenseCarRequest request, Driver driver);
//
//    @AfterMapping
//    protected void afterMappingDriver(@MappingTarget Driver target, DriverRegistrationRequest request, @MappingTarget User user) {
//        target.setUser(user);
//    }
//}
