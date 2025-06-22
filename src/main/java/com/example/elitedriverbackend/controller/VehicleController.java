package com.example.elitedriverbackend.controller;

import com.example.elitedriverbackend.domain.dtos.CreateVehicleDTO;
import com.example.elitedriverbackend.domain.dtos.UpdateVehicleDTO;
import com.example.elitedriverbackend.domain.dtos.VehicleResponseDTO;
import com.example.elitedriverbackend.domain.entity.Vehicle;
import com.example.elitedriverbackend.services.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    @GetMapping("/available")
    public ResponseEntity<List<VehicleResponseDTO>> getAvailableVehicles(
            @RequestParam("startDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

            @RequestParam("endDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        try {
            log.info("Buscando vehículos disponibles entre {} y {}", startDate, endDate);
            List<Vehicle> available = vehicleService.getAvailableVehicles(startDate, endDate);
            List<VehicleResponseDTO> dtos = available.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);

        } catch (Exception e) {
            log.error("Error en getAvailableVehicles: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error obteniendo vehículos disponibles: " + e.getMessage());
        }
    }

    private UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "ID no es un UUID válido: " + id);
        }
    }

    private VehicleResponseDTO convertToDTO(Vehicle v) {
        if (v.getVehicleType() == null) {
            throw new RuntimeException("VehicleType es null para vehículo ID: " + v.getId());
        }

        return VehicleResponseDTO.builder()
                .id(v.getId().toString())
                .name(v.getName())
                .brand(v.getBrand())
                .model(v.getModel())
                .capacity(v.getCapacity())
                .pricePerDay(v.getPricePerDay())
                .kilometers(v.getKilometers())
                .features(v.getFeatures())
                .vehicleType(VehicleResponseDTO.VehicleTypeInfo.builder()
                        .id(v.getVehicleType().getId().toString())
                        .type(v.getVehicleType().getType())
                        .build()
                )
                .kmForMaintenance(v.getKmForMaintenance())
                .status(v.getStatus())
                .mainImageUrl(v.getMainImageUrl())
                .imageUrls(v.getImageUrls())
                .build();
    }

}