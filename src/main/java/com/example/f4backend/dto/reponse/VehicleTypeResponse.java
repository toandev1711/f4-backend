package com.example.f4backend.dto.reponse;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class    VehicleTypeResponse {
    private Integer vehicleTypeId;
    private String vehicleTypeName;
}
