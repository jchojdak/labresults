package com.labresults.userservice.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyExistsException extends RuntimeException  {
    private static final String MESSAGE = "Entity: %s already exists";
    private static final HttpStatus STATUS = HttpStatus.CONFLICT;

    public EntityAlreadyExistsException(String message) {
        super(MESSAGE.formatted(message));
    }

    public HttpStatus getStatus() {
        return STATUS;
    }
}
