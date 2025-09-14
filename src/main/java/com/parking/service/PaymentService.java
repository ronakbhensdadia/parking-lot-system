package com.parking.service;

import com.parking.dto.PaymentReceiptDto;
import com.parking.dto.TicketDto;

public interface PaymentService {
    TicketDto calculateFee(String ticketNumber);

    PaymentReceiptDto processPayment(String ticketNumber);
}