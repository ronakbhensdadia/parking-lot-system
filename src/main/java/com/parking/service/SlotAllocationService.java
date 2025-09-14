package com.parking.service;

import java.util.Optional;

import com.parking.entity.EParkingSlot;
import com.parking.enums.VehicleType;

public interface SlotAllocationService {
    Optional<EParkingSlot> findAvailableSlot(VehicleType vehicleType);
}
