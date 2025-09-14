
// AdminController.java
package com.parking.controller;

import com.parking.dto.AdminActionResponse;
import com.parking.dto.ParkingSlotDto;
import com.parking.dto.PricingRuleDto;
import com.parking.entity.EParkingFloor;
import com.parking.entity.EParkingSlot;
import com.parking.entity.EPricingRule;
import com.parking.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/floors")
    public ResponseEntity<AdminActionResponse> addFloor(@RequestParam int floorNumber) {
	EParkingFloor floor = adminService.addParkingFloor(floorNumber);
	return new ResponseEntity<>(new AdminActionResponse("Floor added successfully", floor), HttpStatus.CREATED);
    }

    @PostMapping("/slots")
    public ResponseEntity<AdminActionResponse> addSlot(@Valid @RequestBody ParkingSlotDto slotDto) {
	EParkingSlot slot = adminService.addParkingSlot(slotDto);
	return new ResponseEntity<>(new AdminActionResponse("Slot added successfully", slot), HttpStatus.CREATED);
    }

    @PutMapping("/pricing")
    public ResponseEntity<AdminActionResponse> updatePricing(@Valid @RequestBody PricingRuleDto pricingRuleDto) {
	EPricingRule rule = adminService.updatePricingRule(pricingRuleDto);
	return new ResponseEntity<>(new AdminActionResponse("Pricing rule updated successfully", rule), HttpStatus.OK);
    }
}