package com.example.elitedriverbackend.domain.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class UpdateCarDTO {
    @NotEmpty
    private String pricePerDay;

    @NotEmpty
    private Integer kilometers;
}
