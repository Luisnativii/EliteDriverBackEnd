package com.example.elitedriverbackend.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarTypeDTO {

    @NotBlank(message = "El tipo de coche no puede estar vacío")
    private String type;
}
