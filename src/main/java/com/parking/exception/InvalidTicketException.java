package com.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidTicketException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 8017844461272267997L;

    public InvalidTicketException(String message) {
	super(message);
    }
}