package com.example.elitedriverbackend.domain.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationDTO {

    @NotBlank(message = "Start date es requerido")
    private Date startDate;

    @NotBlank(message = "End date es requerido")
    private Date endDate;

    @NotBlank(message = "El ID del usuario es requerido")
    private String userId;

    @NotBlank(message = "El ID del vehículo es requerido")
    private String vehicleId;
}
