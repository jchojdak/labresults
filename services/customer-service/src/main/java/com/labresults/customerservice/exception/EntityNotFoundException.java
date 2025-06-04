package com.labresults.customerservice.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Entity: %s not found";
    private static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public EntityNotFoundException(String message) {
        super(MESSAGE.formatted(message));
    }

    public HttpStatus getStatus() {
        return STATUS;
    }
}
