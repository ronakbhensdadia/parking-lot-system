package com.parking.entity;

import com.parking.enums.VehicleType;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "pricing_rule")
@Data
public class EPricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", unique = true, nullable = false)
    private VehicleType vehicleType;

    @Column(name = "base_rate", nullable = false)
    private double baseRate; // e.g., for the first hour

    @Column(name = "hourly_rate", nullable = false)
    private double hourlyRate; // for subsequent hours
}
