package com.example.elitedriverbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private String UUID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String  pricePerDay;

    @Column(nullable = false)
    private Integer kilometers;

    @ManyToOne
    @JoinColumn(
            name = "car_type_id",
            nullable = false
    )
    private CarType carType;
}
