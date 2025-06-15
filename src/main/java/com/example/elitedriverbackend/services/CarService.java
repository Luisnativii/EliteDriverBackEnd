package com.example.elitedriverbackend.services;

import com.example.elitedriverbackend.domain.dtos.CarTypeDTO;
import com.example.elitedriverbackend.domain.dtos.CreateCarDTO;
import com.example.elitedriverbackend.domain.dtos.UpdateCarDTO;
import com.example.elitedriverbackend.domain.entity.Car;
import com.example.elitedriverbackend.domain.entity.CarType;
import com.example.elitedriverbackend.repositories.CarRepository;
import com.example.elitedriverbackend.repositories.CarTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarTypeRepository carTypeRepository;


    public void addCar(CreateCarDTO createCarDTO) {
        Car newCar = new Car();
        newCar.setName(createCarDTO.getName());
        newCar.setBrand(createCarDTO.getBrand());
        newCar.setModel(createCarDTO.getModel());
        newCar.setCapacity(createCarDTO.getCapacity());
        newCar.setPricePerDay(createCarDTO.getPricePerDay());
        newCar.setKilometers(createCarDTO.getKilometers());

        // Mapeo del CarTypeDTO al entity CarType
        String typeName = createCarDTO.getCarType().getType();  // o getCarType(), según tu DTO
        CarType type = carTypeRepository
                .findByType(typeName)
                .orElseThrow(() -> new RuntimeException("Car type '" + typeName + "' no encontrado"));
        newCar.setCarType(type);

        carRepository.save(newCar);
    }


    public void updateCar(UpdateCarDTO updateCarDTO, UUID id) {
        Optional<Car> opCar = carRepository.findById(id);
        if (opCar.isEmpty()) {
            throw new RuntimeException("Car con id " + id + " no encontrado");
        }

        Car carToUpdate = opCar.get();
        carToUpdate.setPricePerDay(updateCarDTO.getPricePerDay());
        carToUpdate.setKilometers(updateCarDTO.getKilometers());
        carRepository.save(carToUpdate);
    }


    public void deleteCar(UUID carId) {
        Optional<Car> opCar = carRepository.findById(carId);
        if (opCar.isEmpty()) {
            throw new RuntimeException("Car con id " + carId + " no encontrado");
        }
        carRepository.delete(opCar.get());
    }


    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    public List<Car> getCarByType(CarTypeDTO carTypeDTO) {
        String typeName = carTypeDTO.getType();
        CarType type = carTypeRepository
                .findByType(typeName)
                .orElseThrow(() -> new RuntimeException("Car type '" + typeName + "' no encontrado"));
        return carRepository.findByCarType(type);
    }


    public List<Car> getCarByCapacity(String capacity) {
        int cap = Integer.parseInt(capacity);
        return carRepository.findByCapacity(cap);
    }
}
