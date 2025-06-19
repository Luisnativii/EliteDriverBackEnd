package com.example.elitedriverbackend.services;

import com.example.elitedriverbackend.domain.dtos.VehicleTypeDTO;
import com.example.elitedriverbackend.domain.dtos.CreateVehicleDTO;
import com.example.elitedriverbackend.domain.dtos.UpdateVehicleDTO;
import com.example.elitedriverbackend.domain.entity.Vehicle;
import com.example.elitedriverbackend.domain.entity.VehicleType;
import com.example.elitedriverbackend.repositories.VehicleRepository;
import com.example.elitedriverbackend.repositories.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    public void addVehicle(CreateVehicleDTO createVehicleDTO) {
        Vehicle newVehicle = new Vehicle();
        newVehicle.setName(createVehicleDTO.getName());
        newVehicle.setBrand(createVehicleDTO.getBrand());
        newVehicle.setModel(createVehicleDTO.getModel());
        newVehicle.setCapacity(createVehicleDTO.getCapacity());
        newVehicle.setPricePerDay(createVehicleDTO.getPricePerDay());
        newVehicle.setKilometers(createVehicleDTO.getKilometers());
        newVehicle.setFeatures(createVehicleDTO.getFeatures());

        String typeName = createVehicleDTO.getVehicleType().getType();
        VehicleType type = vehicleTypeRepository
                .findByType(typeName)
                .orElseThrow(() -> new RuntimeException("Vehicle type '" + typeName + "' no encontrado"));
        newVehicle.setVehicleType(type);

        vehicleRepository.save(newVehicle);
    }

    public void updateVehicle(UpdateVehicleDTO updateVehicleDTO, UUID id) {
        Optional<Vehicle> opVehicle = vehicleRepository.findById(id);
        if (opVehicle.isEmpty()) {
            throw new RuntimeException("Vehicle con id " + id + " no encontrado");
        }

        Vehicle vehicleToUpdate = opVehicle.get();
        vehicleToUpdate.setPricePerDay(updateVehicleDTO.getPricePerDay());
        vehicleToUpdate.setKilometers(updateVehicleDTO.getKilometers());

        if (updateVehicleDTO.getFeatures() != null) {
            vehicleToUpdate.setFeatures(updateVehicleDTO.getFeatures());
        }

        vehicleRepository.save(vehicleToUpdate);
    }

    public void deleteVehicle(UUID vehicleId) {
        Optional<Vehicle> opVehicle = vehicleRepository.findById(vehicleId);
        if (opVehicle.isEmpty()) {
            throw new RuntimeException("Vehicle con id " + vehicleId + " no encontrado");
        }
        vehicleRepository.delete(opVehicle.get());
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