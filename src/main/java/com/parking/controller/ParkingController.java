package com.parking.controller;

import com.parking.dto.PaymentReceiptDto;
import com.parking.dto.TicketDto;
import com.parking.dto.VehicleEntryRequest;
import com.parking.service.ParkingService;
import com.parking.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {

    @Autowired
    ParkingService parkingService;

    @Autowired
    PaymentService paymentService;

    @PostMapping("/entry")
    public ResponseEntity<TicketDto> parkVehicle(@Valid @RequestBody VehicleEntryRequest request) {
	TicketDto ticketDto = parkingService.parkVehicle(request);
	return new ResponseEntity<>(ticketDto, HttpStatus.CREATED);
    }

    @GetMapping("/ticket/{ticketNumber}")
    public ResponseEntity<TicketDto> getTicketDetails(@PathVariable String ticketNumber) {
	return ResponseEntity.ok(parkingService.getParkingDetails(ticketNumber));
    }

    @PostMapping("/exit/{ticketNumber}")
    public ResponseEntity<TicketDto> calculateFee(@PathVariable String ticketNumber) {
	TicketDto ticketWithFee = paymentService.calculateFee(ticketNumber);
	return ResponseEntity.ok(ticketWithFee);
    }

    @PostMapping("/payment/{ticketNumber}")
    public ResponseEntity<PaymentReceiptDto> processPayment(@PathVariable String ticketNumber) {
	PaymentReceiptDto receipt = paymentService.processPayment(ticketNumber);
	return ResponseEntity.ok(receipt);
    }
}
