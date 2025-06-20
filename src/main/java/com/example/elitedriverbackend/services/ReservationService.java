package com.example.elitedriverbackend.services;

import com.example.elitedriverbackend.domain.dtos.CreateReservationDTO;
import com.example.elitedriverbackend.domain.entity.Reservation;
import com.example.elitedriverbackend.domain.entity.User;
import com.example.elitedriverbackend.domain.entity.Vehicle;
import com.example.elitedriverbackend.domain.entity.VehicleType;
import com.example.elitedriverbackend.repositories.ReservationRepository;
import com.example.elitedriverbackend.repositories.UserRepository;
import com.example.elitedriverbackend.repositories.VehicleRepository;
import com.example.elitedriverbackend.repositories.VehicleTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    public void addReservation(CreateReservationDTO createReservationDTO) {

        User user = userRepository.findById(UUID.fromString(createReservationDTO.getUserId()))
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Vehicle vehicle = vehicleRepository.findById(UUID.fromString(createReservationDTO.getVehicleId()))
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado"));

        Reservation newReservation = new Reservation();
        newReservation.setStartDate(createReservationDTO.getStartDate());
        newReservation.setEndDate(createReservationDTO.getEndDate());
        newReservation.setUser(user);
        newReservation.setVehicle(vehicle);
        reservationRepository.save(newReservation);
    }

    public void deleteReservation(UUID id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException("Reserva con id " + id + " no encontrada");
        }
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getAllReservations() {
        try{
            log.info("Obteniendo todas las reservas");
            List<Reservation> reservations = reservationRepository.findAll();

            log.info("Total de Reservas encontradas: {}", reservations.size());

            for (Reservation r : reservations) {
                try{
                    log.info("Reserva ID: {}", r.getId());
                    log.info("Usuario: {}", r.getUser().getFirstName() + " " + r.getUser().getLastName());
                    log.info("Vehículo: {}", r.getVehicle().getName());
                    log.info("Fecha de inicio: {}", r.getStartDate());
                    log.info("Fecha de fin: {}", r.getEndDate());
                } catch (Exception innerEx) {
                    log.error("❌ Error procesando reserva con ID: {}", r.getId(), innerEx);
                }
            }
            return reservations;
        } catch (Exception e){
            log.error("❌ Error obteniendo reservas: ", e);
            throw new RuntimeException("Error obteniendo reservas: " + e.getMessage(), e);

        }
    }

    public Reservation getReservationById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva con id " + id + " no encontrada"));
    }
    // Metodos que faltan
    // GetByDateRange, GetByUser, GetByTypeOfVehicle, GetByVehicle

    public List<Reservation> getReservationByRange(Date startDate, Date endDate) {
        try {
            return reservationRepository.findByStartDateBetween(startDate, endDate);
        }catch (Exception e){
            throw new RuntimeException("Error obteniendo reservas: " + e.getMessage(), e);
        }
    }
    public List<Reservation> getReservationByUser(UUID user) {
        try {
            return reservationRepository.findAll().stream()
                    .filter(reservation -> reservation.getUser().getId().equals(user))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo reservas por usuario: " + e.getMessage(), e);
        }
    }

    public List<Reservation> getReservationByVehicle(UUID vehicle) {
        try {
            return reservationRepository.findAll().stream()
                    .filter(reservation -> reservation.getVehicle().getId().equals(vehicle))
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo reservas por vehículo: " + e.getMessage(), e);
        }
    }

    public List<Reservation> getReservationByVehicleType(String vehicleType) {
        try {
            VehicleType type = vehicleTypeRepository.findByType(vehicleType)
                    .orElseThrow(() -> new RuntimeException("Tipo de vehículo no encontrado"));
            return reservationRepository.findByVehicle_VehicleType(type);
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo reservas por tipo de vehículo: " + e.getMessage(), e);
        }
    }
}
