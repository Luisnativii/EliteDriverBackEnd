package com.example.elitedriverbackend.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "maintenance_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceRecord {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    /**
     * Fecha y hora en que se detectó el mantenimiento requerido.
     */
    @Column(name = "maintenance_date", nullable = false)
    private LocalDateTime maintenanceDate;

    /**
     * Kilómetros en el momento del mantenimiento.
     */
    @Column(name = "km_at_maintenance", nullable = false)
    private Integer kmAtMaintenance;
}
