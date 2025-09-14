package com.parking.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parking.entity.EParkingSlot;
import com.parking.enums.VehicleType;
import com.parking.repository.ParkingSlotRepository;
import com.parking.service.SlotAllocationService;

@Component
public class SlotAllocationServiceImpl implements SlotAllocationService {

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Override
    public Optional<EParkingSlot> findAvailableSlot(VehicleType vehicleType) {
	return parkingSlotRepository.findAvailableSlots(vehicleType).stream().findFirst();
    }
}