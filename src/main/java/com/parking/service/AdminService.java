package com.parking.service;

import com.parking.dto.ParkingSlotDto;
import com.parking.dto.PricingRuleDto;
import com.parking.entity.EParkingFloor;
import com.parking.entity.EParkingSlot;
import com.parking.entity.EPricingRule;

public interface AdminService {
    EParkingFloor addParkingFloor(int floorNumber);

    EParkingSlot addParkingSlot(ParkingSlotDto parkingSlotDto);

    EPricingRule updatePricingRule(PricingRuleDto pricingRuleDto);
}