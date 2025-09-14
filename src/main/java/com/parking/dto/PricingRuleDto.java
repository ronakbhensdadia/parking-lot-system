package com.parking.dto;

import com.parking.enums.VehicleType;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PricingRuleDto {
    
    @NotNull
    private VehicleType vehicleType;
    
    @NotNull
    @Positive
    private Double baseRate;
    
    @NotNull
    @Positive
    private Double hourlyRate;
}