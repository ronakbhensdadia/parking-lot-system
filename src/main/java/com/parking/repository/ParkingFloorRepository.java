package com.parking.repository;

import com.parking.entity.EParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingFloorRepository extends JpaRepository<EParkingFloor, Long> {
}
