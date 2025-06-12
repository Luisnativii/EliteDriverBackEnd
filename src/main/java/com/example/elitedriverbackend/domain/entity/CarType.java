package com.example.elitedriverbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
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
