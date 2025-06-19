package com.example.elitedriverbackend.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Data
@Getter
public class UpdateVehicleDTO {
    @NotEmpty
    private BigDecimal pricePerDay;

    @NotEmpty
    private Integer kilometers;

    // Nuevo campo para actualizar características del vehículo
    private List<String> features;
}