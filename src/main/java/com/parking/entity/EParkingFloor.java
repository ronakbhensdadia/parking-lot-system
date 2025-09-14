package com.parking.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "parking_floor")
@Data
public class EParkingFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "floor_number", nullable = false)
    private int floorNumber;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
    private List<EParkingSlot> slots;
}