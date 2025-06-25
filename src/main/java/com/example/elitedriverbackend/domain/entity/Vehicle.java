package com.example.elitedriverbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    @JdbcTypeCode(SqlTypes.UUID)
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
    @CollectionTable(
            name = "vehicle_features",
            joinColumns = @JoinColumn(name = "vehicle_id")
    )
    @Column(name = "feature", nullable = false)
    private List<String> features = new ArrayList<>();

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

    @Column(name = "main_image_url")
    private String mainImageUrl;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "vehicle_images",
            joinColumns = @JoinColumn(name = "vehicle_id")
    )
    @Column(name = "image_url", nullable = false)
    private List<String> imageUrls = new ArrayList<>();

    // =============================================
    // MÉTODOS DE MANTENIMIENTO (DENTRO DE LA CLASE)
    // =============================================

    /**
     * Calcula el próximo kilómetraje de mantenimiento
     */
    public Integer getNextMaintenanceKm() {
        if (kilometers == null || kmForMaintenance == null) {
            return null;
        }
        int cycles = (int) Math.ceil((double) kilometers / kmForMaintenance);
        return cycles * kmForMaintenance;
    }

    /**
     * Calcula cuántos kilómetros faltan para el próximo mantenimiento
     */
    public Integer getKmUntilMaintenance() {
        Integer nextMaintenance = getNextMaintenanceKm();
        if (nextMaintenance == null || kilometers == null) {
            return null;
        }
        return Math.max(0, nextMaintenance - kilometers);
    }

    /**
     * Verifica si el vehículo necesita mantenimiento basado en intervalos
     */
    public boolean needsMaintenance() {
        if (kilometers == null || kmForMaintenance == null) {
            return false;
        }
        return kilometers % kmForMaintenance == 0 && kilometers > 0;
    }

    /**
     * Obtiene el número de ciclos de mantenimiento completados
     */
    public Integer getCompletedMaintenanceCycles() {
        if (kilometers == null || kmForMaintenance == null) {
            return 0;
        }
        return kilometers / kmForMaintenance;
    }
}