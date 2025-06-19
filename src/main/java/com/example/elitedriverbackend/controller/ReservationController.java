package com.example.elitedriverbackend.controller;

import com.example.elitedriverbackend.domain.dtos.CreateReservationDTO;
import com.example.elitedriverbackend.domain.dtos.ReservationResponseDTO;
import com.example.elitedriverbackend.domain.entity.Reservation;
import com.example.elitedriverbackend.services.ReservationService;
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
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody CreateReservationDTO dto) {
        reservationService.addReservation(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable String id) {
        UUID uuid = parseUUID(id);
        Reservation reservation = reservationService.getReservationById(uuid);
        return new ResponseEntity<>(reservation, HttpStatus.OK);

    }

    private UUID parseUUID(String id) {
        try{
            return UUID.fromString(id);
        }catch(IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id no es un UUID válido: " + id);
        }
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
        try{
            List<Reservation> reservations = reservationService.getAllReservations();

            List<ReservationResponseDTO> reservationDTOs = reservations.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(reservationDTOs);
        }catch (Exception e){
            log.error("Error en getAllReservations: ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error en getAllReservations: " + e.getMessage());
        }
    }

    private ReservationResponseDTO convertToDTO(Reservation reservation) {
        if (reservation.getVehicle() == null) {
            throw new RuntimeException("Reservation con id " + reservation.getUUID() + " no tiene vehículo asociado");
        }

        return ReservationResponseDTO.builder()
                .id(reservation.getUUID())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .user(ReservationResponseDTO.UserInfo.builder()
                        .id(String.valueOf(reservation.getUser().getId()))
                        .firstName(reservation.getUser().getFirstName())
                        .lastName(reservation.getUser().getLastName())
                        .build())
                .vehicle(ReservationResponseDTO.VehicleInfo.builder()
                        .id(String.valueOf(reservation.getVehicle().getId()))
                        .name(reservation.getVehicle().getName())
                        .brand(reservation.getVehicle().getBrand())
                        .build())
                .build();

    }
}
