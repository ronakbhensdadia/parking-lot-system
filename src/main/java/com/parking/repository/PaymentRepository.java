package com.parking.repository;

import com.parking.entity.EPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<EPayment, Long> {
}