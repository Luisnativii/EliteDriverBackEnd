package com.example.elitedriverbackend.controller;

import com.example.elitedriverbackend.domain.dtos.CreateCarDTO;
import com.example.elitedriverbackend.domain.dtos.UpdateCarDTO;
import com.example.elitedriverbackend.domain.entity.Car;
import com.example.elitedriverbackend.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarsController {

    private final CarService carService;


    @PostMapping
    public ResponseEntity<Void> addCar(@Valid @RequestBody CreateCarDTO dto) {
        carService.addCar(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCarDTO dto) {
        carService.updateCar(dto, id);
        return ResponseEntity.ok().build();
    }


    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> all = carService.getAllCars();
        return ResponseEntity.ok(all);
    }
}

