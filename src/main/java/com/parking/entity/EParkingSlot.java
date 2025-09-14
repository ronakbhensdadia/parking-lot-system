package com.parking.entity;

import com.parking.enums.VehicleType;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "parking_slot")
@Data
public class EParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "slot_number", nullable = false, unique = true)
    private int slotNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Column(name = "is_occupied", nullable = false)
    private boolean isOccupied = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    private EParkingFloor floor;
}