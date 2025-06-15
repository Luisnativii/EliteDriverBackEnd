package com.example.elitedriverbackend.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateCarDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "La marca no puede estar vacía")
    private String brand;

    @NotBlank(message = "El modelo no puede estar vacío")
    private String model;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacity;

    @NotNull(message = "El precio por día es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal pricePerDay;

    @NotNull(message = "Los kilómetros son obligatorios")
    @Min(value = 0, message = "Los kilómetros no pueden ser negativos")
    private Integer kilometers;

    @Valid
    @NotNull(message = "El tipo de coche es obligatorio")
    private CarTypeDTO carType;
}
