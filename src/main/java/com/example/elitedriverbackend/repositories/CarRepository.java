package com.example.elitedriverbackend.repositories;

import com.example.elitedriverbackend.domain.entity.Car;
import com.example.elitedriverbackend.domain.entity.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

    List<Car> findByCarType(CarType carType);


    List<Car> findByCapacity(int capacity);
}
