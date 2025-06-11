package com.example.elitedriverbackend.services;

import com.example.elitedriverbackend.domain.dtos.CreateCarDTO;
import com.example.elitedriverbackend.domain.dtos.UpdateCarDTO;
import com.example.elitedriverbackend.domain.entity.Car;
import com.example.elitedriverbackend.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public void addCar(CreateCarDTO createCarDTO) {
        Car newCar = new Car();
        newCar.setName(createCarDTO.getName());
        newCar.setBrand(createCarDTO.getBrand());
        newCar.setModel(createCarDTO.getModel());
        newCar.setCapacity(createCarDTO.getCapacity());
        newCar.setPricePerDay(createCarDTO.getPricePerDay());
        newCar.setKilometers(createCarDTO.getKilometers());
        carRepository.save(newCar);
    }

    public void updateCar(UpdateCarDTO updateCarDTO, UUID id) {
        Optional<Car> opCar = carRepository.findById(id);
        if(opCar.isEmpty()){
            throw new RuntimeException("Car not found");
        }

        Car carToUpdate = opCar.get();
        carToUpdate.setPricePerDay(updateCarDTO.getPricePerDay());
        carToUpdate.setKilometers(updateCarDTO.getKilometers());
        carRepository.save(carToUpdate);
    }

    public void deleteCar(UUID carId) {
        Optional<Car> opCar = carRepository.findById(carId);
        if(opCar.isEmpty()){
            throw new RuntimeException("Car not found");
        }
        Car carToDelete = opCar.get();
        carRepository.delete(carToDelete);
    }

    public List<Car> getCarByType(String carType) {
        return carRepository.findByCarType(carType);
    }

    public List<Car> getCarByCapacity(String capacity) {
        return carRepository.findByCapacity(capacity);
    }

}
