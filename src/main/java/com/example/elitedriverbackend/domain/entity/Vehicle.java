package com.example.elitedriverbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicles")
@EqualsAndHashCode(exclude = "vehicleType")
@ToString(exclude = "vehicleType")
public class Vehicle {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)  // Changed from VARCHAR to UUID
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private BigDecimal pricePerDay;

    @Column(nullable = false)
    private Integer kilometers;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "vehicle_features", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "feature")
    private List<String> features;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "vehicle_type_id",
            nullable = false
    )
    private VehicleType vehicleType;

    @Column(name = "km_for_maintenance")
    private Integer kmForMaintenance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VehicleStatus status;

    // ← Aquí agregamos la URL de la imagen principal
    @Column(name = "main_image_url")
    private String mainImageUrl;

    // ← Y aquí la lista de URLs secundarias
    @ElementCollection
    @CollectionTable(
            name = "vehicle_images",
            joinColumns = @JoinColumn(name = "vehicle_id")
    )
    @Column(name = "image_url")
    private List<String> imageUrls;

}