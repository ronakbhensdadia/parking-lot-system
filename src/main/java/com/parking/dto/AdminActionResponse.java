package com.parking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminActionResponse {
    
    private String message;
    
    private Object data;
}
