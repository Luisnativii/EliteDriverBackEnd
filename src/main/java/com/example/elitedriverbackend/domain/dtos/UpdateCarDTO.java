package com.example.elitedriverbackend.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;

@Data
@Getter
public class UpdateCarDTO {
    @NotEmpty
    private BigDecimal pricePerDay;

    @NotEmpty
    private Integer kilometers;
}
