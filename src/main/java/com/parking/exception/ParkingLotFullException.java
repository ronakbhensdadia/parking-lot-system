package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ParkingLotFullException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 628769031622970566L;

    public ParkingLotFullException(String message) {
	super(message);
    }
}