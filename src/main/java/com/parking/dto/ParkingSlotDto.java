package com.parking.dto;

import com.parking.enums.VehicleType;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ParkingSlotDto {
    
    @NotNull
    @Positive
    private Integer slotNumber;
    
    @NotNull
    private VehicleType vehicleType;
    
    @NotNull
    @Positive
    private Long floorId;
}