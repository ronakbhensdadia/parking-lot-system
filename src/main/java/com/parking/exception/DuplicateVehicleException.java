package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateVehicleException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = -6263728406183689582L;

    public DuplicateVehicleException(String message) {
	super(message);
    }
}