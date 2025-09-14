package com.parking.entity;

import com.parking.enums.PaymentStatus;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
public class EPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private ETicket ticket;
}
