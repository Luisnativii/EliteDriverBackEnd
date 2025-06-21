package com.example.elitedriverbackend.domain.dtos;

import com.example.elitedriverbackend.domain.entity.VehicleStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
public class UpdateVehicleDTO {

    @NotNull(message = "El precio por día no puede ser nulo")
    @Positive(message = "El precio por día debe ser mayor a 0")
    private BigDecimal pricePerDay;

    @NotNull(message = "Los kilómetros no pueden ser nulos")
    @Min(value = 0, message = "Los kilómetros no pueden ser negativos")
    private Integer kilometers;

    // Nuevo campo para actualizar características del vehículo
    private List<String> features;

    private Integer kmForMaintenance;
    private VehicleStatus status;

    private String mainImageUrl;
    private List<String> imageUrls;
}