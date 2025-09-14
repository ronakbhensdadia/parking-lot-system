package com.parking.dto;

import com.parking.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentReceiptDto {
    
    private String transactionId;
    
    private String ticketNumber;
    
    private String vehiclePlateNumber;
    
    private double amountPaid;
    
    private LocalDateTime paymentTime;
    
    private PaymentStatus paymentStatus;
    
    private String message;
}