package com.example.elitedriverbackend.services;

import com.example.elitedriverbackend.domain.dtos.CreateReservationDTO;
import com.example.elitedriverbackend.domain.entity.Reservation;
import com.example.elitedriverbackend.domain.entity.User;
import com.example.elitedriverbackend.domain.entity.Vehicle;
import com.example.elitedriverbackend.repositories.ReservationRepository;
import com.example.elitedriverbackend.repositories.UserRepository;
import com.example.elitedriverbackend.repositories.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                    log.info("Reserva ID: {}", r.getUUID());
                    log.info("Usuario: {}", r.getUser().getFirstName() + " " + r.getUser().getLastName());
                    log.info("Vehículo: {}", r.getVehicle().getName());
                    log.info("Fecha de inicio: {}", r.getStartDate());
                    log.info("Fecha de fin: {}", r.getEndDate());
                } catch (Exception innerEx) {
                    log.error("❌ Error procesando reserva con ID: {}", r.getUUID(), innerEx);
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
}
