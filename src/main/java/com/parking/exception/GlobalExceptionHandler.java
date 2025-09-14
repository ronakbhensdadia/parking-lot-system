package com.parking.exception;

import com.parking.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
	ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
		ex.getMessage(), request.getDescription(false));
	return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ParkingLotFullException.class)
    public ResponseEntity<ErrorResponse> handleParkingLotFullException(ParkingLotFullException ex, WebRequest request) {
	return buildErrorResponse(ex, HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(InvalidTicketException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTicketException(InvalidTicketException ex, WebRequest request) {
	return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DuplicateVehicleException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateVehicleException(DuplicateVehicleException ex,
	    WebRequest request) {
	return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
	return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}