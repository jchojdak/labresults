package com.labresults.sampleservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = ex.getStatus();

        CustomExceptionResponse response = new CustomExceptionResponse();
        response.setPath(request.getRequestURI());
        response.setError(status.name());
        response.setMessage(ex.getMessage());
        response.setStatus(status.value());

        return new ResponseEntity<>(response, status);
    }
}