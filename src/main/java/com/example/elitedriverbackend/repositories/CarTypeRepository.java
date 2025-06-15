package com.example.elitedriverbackend.repositories;

import com.example.elitedriverbackend.domain.entity.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface CarTypeRepository extends JpaRepository<CarType, UUID> {

    Optional<CarType> findByType(String type);
}
