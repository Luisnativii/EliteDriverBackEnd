package com.example.elitedriverbackend.repositories;

import com.example.elitedriverbackend.domain.dtos.CarTypeDTO;
import com.example.elitedriverbackend.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    public List<Car> findByCarType(CarTypeDTO carType);
    public List<Car> findByCapacity(Integer capacity);
}
