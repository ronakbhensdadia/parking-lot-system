package com.parking.service.impl;

import com.parking.dto.ParkingSlotDto;
import com.parking.dto.PricingRuleDto;
import com.parking.entity.EParkingFloor;
import com.parking.entity.EParkingSlot;
import com.parking.entity.EPricingRule;
import com.parking.exception.InvalidTicketException;
import com.parking.repository.ParkingFloorRepository;
import com.parking.repository.ParkingSlotRepository;
import com.parking.repository.PricingRuleRepository;
import com.parking.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    ParkingFloorRepository floorRepository;

    @Autowired
    ParkingSlotRepository slotRepository;

    @Autowired
    PricingRuleRepository pricingRuleRepository;

    @Override
    @Transactional
    public EParkingFloor addParkingFloor(int floorNumber) {
	EParkingFloor floor = new EParkingFloor();
	floor.setFloorNumber(floorNumber);
	return floorRepository.save(floor);
    }

    @Override
    @Transactional
    public EParkingSlot addParkingSlot(ParkingSlotDto parkingSlotDto) {
	EParkingFloor floor = floorRepository.findById(parkingSlotDto.getFloorId()).orElseThrow(
		() -> new InvalidTicketException("Parking floor not found with id: " + parkingSlotDto.getFloorId()));

	EParkingSlot slot = new EParkingSlot();
	slot.setSlotNumber(parkingSlotDto.getSlotNumber());
	slot.setVehicleType(parkingSlotDto.getVehicleType());
	slot.setFloor(floor);
	slot.setOccupied(false);

	return slotRepository.save(slot);
    }

    @Override
    @Transactional
    public EPricingRule updatePricingRule(PricingRuleDto pricingRuleDto) {
	EPricingRule rule = pricingRuleRepository.findByVehicleType(pricingRuleDto.getVehicleType())
		.orElse(new EPricingRule());

	rule.setVehicleType(pricingRuleDto.getVehicleType());
	rule.setBaseRate(pricingRuleDto.getBaseRate());
	rule.setHourlyRate(pricingRuleDto.getHourlyRate());

	return pricingRuleRepository.save(rule);
    }
}