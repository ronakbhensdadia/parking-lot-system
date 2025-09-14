package com.parking.dto;

import com.parking.enums.TicketStatus;
import com.parking.enums.VehicleType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TicketDto {
    
    private String ticketNumber;
    
    private int slotNumber;
    
    private int floorNumber;
    
    private String vehiclePlateNumber;
    
    private VehicleType vehicleType;
    
    private LocalDateTime entryTime;
    
    private LocalDateTime exitTime;
    
    private TicketStatus status;
    
    private Double amount;
}