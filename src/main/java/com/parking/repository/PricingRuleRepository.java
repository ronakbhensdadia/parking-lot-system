package com.parking.repository;

import com.parking.entity.EPricingRule;
import com.parking.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PricingRuleRepository extends JpaRepository<EPricingRule, Long> {
    
    Optional<EPricingRule> findByVehicleType(VehicleType vehicleType);
}