package com.parking.dto;

import com.parking.enums.VehicleType;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VehicleEntryRequest {
    
    @NotBlank(message = "Vehicle plate number cannot be blank")
    private String plateNumber;
    
    @NotNull(message = "Vehicle type cannot be null")
    private VehicleType vehicleType;
}