package com.example.elitedriverbackend.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Getter
public class CreateCarDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @NotEmpty
    private Integer capacity;

    @NotEmpty
    private BigDecimal pricePerDay;

    @NotEmpty
    private Integer kilometers;

    private CarTypeDTO carType;
}
