package com.example.elitedriverbackend.services;

import com.example.elitedriverbackend.domain.dtos.CreateVehicleDTO;
import com.example.elitedriverbackend.domain.dtos.UpdateVehicleDTO;
import com.example.elitedriverbackend.domain.dtos.VehicleTypeDTO;
import com.example.elitedriverbackend.domain.entity.Vehicle;
import com.example.elitedriverbackend.domain.entity.VehicleStatus;
import com.example.elitedriverbackend.domain.entity.VehicleType;
import com.example.elitedriverbackend.repositories.VehicleRepository;
import com.example.elitedriverbackend.repositories.VehicleTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    public void addVehicle(CreateVehicleDTO dto) {
        Vehicle newVehicle = new Vehicle();
        newVehicle.setName(dto.getName());
        newVehicle.setBrand(dto.getBrand());
        newVehicle.setModel(dto.getModel());
        newVehicle.setCapacity(dto.getCapacity());
        newVehicle.setPricePerDay(dto.getPricePerDay());
        newVehicle.setKilometers(dto.getKilometers());
        newVehicle.setFeatures(dto.getFeatures());

        // km para mantenimiento
        newVehicle.setKmForMaintenance(dto.getKmForMaintenance());
        // estado inicial
        newVehicle.setStatus(VehicleStatus.maintenanceCompleted);

        String typeName = dto.getVehicleType().getType();
        VehicleType type = vehicleTypeRepository
                .findByType(typeName)
                .orElseThrow(() -> new RuntimeException("Vehicle type '" + typeName + "' no encontrado"));
        newVehicle.setVehicleType(type);

        vehicleRepository.save(newVehicle);
    }

    public void updateVehicle(UpdateVehicleDTO dto, UUID id) {
        Vehicle vehicleToUpdate = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle con id " + id + " no encontrado"));

        if (dto.getPricePerDay() != null) {
            vehicleToUpdate.setPricePerDay(dto.getPricePerDay());
        }
        if (dto.getKilometers() != null) {
            vehicleToUpdate.setKilometers(dto.getKilometers());
        }
        if (dto.getFeatures() != null) {
            vehicleToUpdate.setFeatures(dto.getFeatures());
        }
        if (dto.getKmForMaintenance() != null) {
            vehicleToUpdate.setKmForMaintenance(dto.getKmForMaintenance());
        }

        // si superó kmForMaintenance ⇒ maintenanceRequired
        if (vehicleToUpdate.getKilometers() >= vehicleToUpdate.getKmForMaintenance()) {
            vehicleToUpdate.setStatus(VehicleStatus.maintenanceRequired);
        }
        // de lo contrario, si viene status en el DTO, lo usa
        else if (dto.getStatus() != null) {
            vehicleToUpdate.setStatus(dto.getStatus());
        }

        vehicleRepository.save(vehicleToUpdate);
    }

    public void deleteVehicle(UUID vehicleId) {
        Vehicle v = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle con id " + vehicleId + " no encontrado"));
        vehicleRepository.delete(v);
    }

    public List<Vehicle> getAllVehicles() {
        try {
            log.info("Obteniendo todos los vehículos");
            List<Vehicle> vehicles = vehicleRepository.findAll();
            log.info("Total vehículos recuperados: {}", vehicles.size());
            for (Vehicle v : vehicles) {
                try {
                    log.info("Vehículo ID: {}", v.getId());
                    log.info("  Nombre: {}", v.getName());
                    log.info("  Marca: {}", v.getBrand());
                    log.info("  Modelo: {}", v.getModel());
                    log.info("  Tipo: {}", v.getVehicleType() != null ? v.getVehicleType().getType() : "NULL");
                    log.info("  Tipo ID: {}", v.getVehicleType() != null ? v.getVehicleType().getId() : "NULL");
                } catch (Exception innerEx) {
                    log.error("❌ Error procesando vehículo con ID: {}", v.getId(), innerEx);
                }
            }
            return vehicles;
        } catch (Exception e) {
            log.error("❌ Error obteniendo vehículos: ", e);
            throw new RuntimeException("Error al obtener vehículos: " + e.getMessage(), e);
        }
    }

    public Vehicle getVehicleById(UUID id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle con id " + id + " no encontrado"));
    }

    public List<Vehicle> getVehicleByType(VehicleTypeDTO vehicleTypeDTO) {
        String typeName = vehicleTypeDTO.getType();
        VehicleType type = vehicleTypeRepository
                .findByType(typeName)
                .orElseThrow(() -> new RuntimeException("Vehicle type '" + typeName + "' no encontrado"));
        return vehicleRepository.findByVehicleType(type);
    }

    public List<Vehicle> getVehicleByCapacity(String capacity) {
        int cap = Integer.parseInt(capacity);
        return vehicleRepository.findByCapacity(cap);
    }

}
