package com.parking.service.impl;

import com.parking.dto.PaymentReceiptDto;
import com.parking.dto.TicketDto;
import com.parking.entity.EPayment;
import com.parking.entity.EPricingRule;
import com.parking.entity.ETicket;
import com.parking.enums.PaymentStatus;
import com.parking.enums.TicketStatus;
import com.parking.exception.InvalidTicketException;
import com.parking.repository.ParkingSlotRepository;
import com.parking.repository.PaymentRepository;
import com.parking.repository.PricingRuleRepository;
import com.parking.repository.TicketRepository;
import com.parking.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    PricingRuleRepository pricingRuleRepository;

    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Override
    @Transactional
    public TicketDto calculateFee(String ticketNumber) {
	ETicket ticket = findActiveTicket(ticketNumber);

	EPricingRule rule = pricingRuleRepository.findByVehicleType(ticket.getVehicleType()).orElseThrow(
		() -> new IllegalStateException("No pricing rule found for vehicle type: " + ticket.getVehicleType()));

	LocalDateTime now = LocalDateTime.now();
	Duration duration = Duration.between(ticket.getEntryTime(), now);
	long hours = duration.toHours();
	if (duration.toMinutes() % 60 > 0) {
	    hours++; // Round up to the next hour
	}

	// Simple pricing: first hour at base rate, subsequent hours at hourly rate.
	double amount = 0;
	if (hours > 0) {
	    amount = rule.getBaseRate() + (hours - 1) * rule.getHourlyRate();
	} else if (hours == 0) {
	    amount = rule.getBaseRate();
	}

	ticket.setAmount(amount);
	ticket.setExitTime(now);
	ticket.setStatus(TicketStatus.PENDING); // Update status to reflect fee is calculated and pending payment
	ETicket updatedTicket = ticketRepository.save(ticket);
	return mapToTicketDto(updatedTicket);
    }

    @Override
    @Transactional
    public PaymentReceiptDto processPayment(String ticketNumber) {
	ETicket ticket = ticketRepository.findByTicketNumber(ticketNumber)
		.filter(t -> t.getStatus() == TicketStatus.PENDING).orElseThrow(() -> new InvalidTicketException(
			"Ticket not ready for payment or not found. Please calculate fee first."));

	// Simulate payment gateway interaction
	boolean paymentSuccess = simulatePaymentGateway();

	EPayment payment = new EPayment();
	payment.setTicket(ticket);
	payment.setAmount(ticket.getAmount());
	payment.setTimestamp(LocalDateTime.now());
	payment.setTransactionId(UUID.randomUUID().toString());

	if (paymentSuccess) {
	    payment.setStatus(PaymentStatus.SUCCESS);

	    // Free up the slot
	    ticket.getParkingSlot().setOccupied(false);
	    parkingSlotRepository.save(ticket.getParkingSlot());

	    // Update ticket status
	    ticket.setStatus(TicketStatus.EXITED);
	    ticketRepository.save(ticket);

	    paymentRepository.save(payment);

	    return PaymentReceiptDto.builder().transactionId(payment.getTransactionId())
		    .ticketNumber(ticket.getTicketNumber()).vehiclePlateNumber(ticket.getVehiclePlateNumber())
		    .amountPaid(ticket.getAmount()).paymentTime(payment.getTimestamp())
		    .paymentStatus(PaymentStatus.SUCCESS).message("Payment successful. You may exit now.").build();
	} else {
	    payment.setStatus(PaymentStatus.FAILED);
	    paymentRepository.save(payment);
	    // NOTE: The transaction will commit this failed payment record, but because we
	    // throw an exception,
	    // any other changes within a larger transaction would be rolled back.
	    // Here, we just return a failure DTO. The slot remains occupied.
	    return PaymentReceiptDto.builder().transactionId(payment.getTransactionId())
		    .ticketNumber(ticket.getTicketNumber()).amountPaid(0).paymentTime(payment.getTimestamp())
		    .paymentStatus(PaymentStatus.FAILED).message("Payment failed. Please try again.").build();
	}
    }

    private boolean simulatePaymentGateway() {
	// In a real application, this would integrate with a payment provider.
	// For this demo, we'll just assume it's always successful.
	return true;
    }

    private ETicket findActiveTicket(String ticketNumber) {
	return ticketRepository.findByTicketNumber(ticketNumber)
		.filter(t -> (t.getStatus() == TicketStatus.ACTIVE || t.getStatus() == TicketStatus.PENDING))
		.orElseThrow(() -> new InvalidTicketException("Active ticket not found for number: " + ticketNumber));
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