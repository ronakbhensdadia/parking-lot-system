package com.parking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parking.entity.ETicket;
import com.parking.enums.TicketStatus;

public interface TicketRepository extends JpaRepository<ETicket, Long> {
    
    Optional<ETicket> findByTicketNumber(String ticketNumber);

    Optional<ETicket> findByVehiclePlateNumberAndStatus(String plateNumber, TicketStatus status);
}