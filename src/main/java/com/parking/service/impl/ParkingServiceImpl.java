package com.parking.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleEntryRequest;
import com.parking.entity.EParkingSlot;
import com.parking.entity.ETicket;
import com.parking.enums.TicketStatus;
import com.parking.exception.DuplicateVehicleException;
import com.parking.exception.InvalidTicketException;
import com.parking.exception.ParkingLotFullException;
import com.parking.repository.ParkingSlotRepository;
import com.parking.repository.TicketRepository;
import com.parking.service.ParkingService;
import com.parking.service.SlotAllocationService;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    TicketRepository ticketRepository;
    
    @Autowired
    ParkingSlotRepository parkingSlotRepository;
    
    @Autowired
    SlotAllocationService slotAllocationService;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmmss");

    @Override
    @Transactional
    public TicketDto parkVehicle(VehicleEntryRequest request) {
	// 1. Check for duplicate entry
	ticketRepository.findByVehiclePlateNumberAndStatus(request.getPlateNumber(), TicketStatus.ACTIVE)
		.ifPresent(ticket -> {
		    throw new DuplicateVehicleException(
			    "Vehicle with plate number " + request.getPlateNumber() + " is already parked.");
		});

	// 2. Find available slot using the defined strategy
	EParkingSlot availableSlot = slotAllocationService.findAvailableSlot(request.getVehicleType()).orElseThrow(
		() -> new ParkingLotFullException("Parking lot is full for vehicle type: " + request.getVehicleType()));

	// 3. Occupy the slot and create a ticket
	availableSlot.setOccupied(true);
	parkingSlotRepository.save(availableSlot);

	ETicket ticket = new ETicket();
	// ticket format --> (Timestamp+4 digit Uppercase UUID)
	ticket.setTicketNumber(LocalDateTime.now().format(DATE_FORMATTER) + "-"
		+ UUID.randomUUID().toString().substring(0, 4).toUpperCase());
	ticket.setVehiclePlateNumber(request.getPlateNumber());
	ticket.setVehicleType(request.getVehicleType());
	ticket.setEntryTime(LocalDateTime.now());
	ticket.setStatus(TicketStatus.ACTIVE);
	ticket.setParkingSlot(availableSlot);

	ETicket savedTicket = ticketRepository.save(ticket);

	return mapToTicketDto(savedTicket);
    }

    @Override
    public TicketDto getParkingDetails(String ticketNumber) {
	ETicket ticket = ticketRepository.findByTicketNumber(ticketNumber)
		.orElseThrow(() -> new InvalidTicketException("Ticket not found for number: " + ticketNumber));
	return mapToTicketDto(ticket);
    }

    private TicketDto mapToTicketDto(ETicket ticket) {
	return TicketDto.builder().ticketNumber(ticket.getTicketNumber())
		.slotNumber(ticket.getParkingSlot().getSlotNumber())
		.floorNumber(ticket.getParkingSlot().getFloor().getFloorNumber())
		.vehiclePlateNumber(ticket.getVehiclePlateNumber()).vehicleType(ticket.getVehicleType())
		.entryTime(ticket.getEntryTime()).exitTime(ticket.getExitTime()).status(ticket.getStatus())
		.amount(ticket.getAmount()).build();
    }
}
