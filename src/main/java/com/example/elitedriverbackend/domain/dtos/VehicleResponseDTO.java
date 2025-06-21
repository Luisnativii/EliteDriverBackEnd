package com.example.elitedriverbackend.domain.dtos;

import com.example.elitedriverbackend.domain.entity.VehicleStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponseDTO {
    private String id;
    private String name;
    private String brand;
    private String model;
    private Integer capacity;
    private BigDecimal pricePerDay;
    private Integer kilometers;
    private List<String> features;

    // Solo incluimos la información básica del tipo, no toda la entidad
    private VehicleTypeInfo vehicleType;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VehicleTypeInfo {
        private String id;
        private String type;
    }

    private Integer kmForMaintenance;
    private VehicleStatus status;

}