package com.parking.service;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleEntryRequest;

public interface ParkingService {
    TicketDto parkVehicle(VehicleEntryRequest request);

    TicketDto getParkingDetails(String ticketNumber);
}