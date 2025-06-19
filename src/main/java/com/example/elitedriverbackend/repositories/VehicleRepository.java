package com.example.elitedriverbackend.repositories;

import com.example.elitedriverbackend.domain.entity.Vehicle;
import com.example.elitedriverbackend.domain.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {

    List<Vehicle> findByVehicleType(VehicleType vehicleType); // Cambiado de findByCarType

    List<Vehicle> findByCapacity(int capacity);
}