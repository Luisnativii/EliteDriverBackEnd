package com.example.elitedriverbackend.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "car_types")
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String UUID;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "carType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Car> cars;
}
