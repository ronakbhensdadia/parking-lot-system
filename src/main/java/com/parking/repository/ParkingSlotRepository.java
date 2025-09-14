package com.parking.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.parking.entity.EParkingSlot;
import com.parking.enums.VehicleType;

public interface ParkingSlotRepository extends JpaRepository<EParkingSlot, Long> {

    /**
     * Finds available parking slots for a given vehicle type, ordered by floor and
     * slot number.
     * 
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ps FROM EParkingSlot ps WHERE ps.vehicleType = :vehicleType AND ps.isOccupied = false "
	    + "ORDER BY ps.floor.floorNumber, ps.slotNumber")
    List<EParkingSlot> findAvailableSlots(VehicleType vehicleType);

}