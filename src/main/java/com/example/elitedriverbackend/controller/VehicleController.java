package com.example.elitedriverbackend.controller;

import com.example.elitedriverbackend.domain.dtos.CreateVehicleDTO;
import com.example.elitedriverbackend.domain.dtos.UpdateVehicleDTO;
import com.example.elitedriverbackend.domain.dtos.VehicleResponseDTO;
import com.example.elitedriverbackend.domain.entity.Vehicle;
import com.example.elitedriverbackend.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Void> addVehicle(@Valid @RequestBody CreateVehicleDTO dto) {
        vehicleService.addVehicle(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVehicle(
            @PathVariable String id,
            @Valid @RequestBody UpdateVehicleDTO dto) {
        UUID uuid = parseUUID(id);
        vehicleService.updateVehicle(dto, uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        try {
            List<Vehicle> vehicles = vehicleService.getAllVehicles();

            List<VehicleResponseDTO> vehicleDTOs = vehicles.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(vehicleDTOs);

        } catch (Exception e) {
            log.error("Error en getAllVehicles: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error obteniendo vehículos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable String id) {
        UUID uuid = parseUUID(id);
        Vehicle vehicle = vehicleService.getVehicleById(uuid);
        return ResponseEntity.ok(convertToDTO(vehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        UUID uuid = parseUUID(id);
        vehicleService.deleteVehicle(uuid);
        return ResponseEntity.ok().build();
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID no es un UUID válido: " + id);
        }
    }

    private VehicleResponseDTO convertToDTO(Vehicle vehicle) {
        // Verificaciones de seguridad
        if (vehicle.getVehicleType() == null) {
            throw new RuntimeException("VehicleType es null para vehículo ID: " + vehicle.getId());
        }

        return VehicleResponseDTO.builder()
                .id(vehicle.getId().toString())
                .name(vehicle.getName())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .capacity(vehicle.getCapacity())
                .pricePerDay(vehicle.getPricePerDay())
                .kilometers(vehicle.getKilometers())
                .features(vehicle.getFeatures())
                .vehicleType(VehicleResponseDTO.VehicleTypeInfo.builder()
                        .id(vehicle.getVehicleType().getId().toString())
                        .type(vehicle.getVehicleType().getType())
                        .build())
                .build();
    }
}