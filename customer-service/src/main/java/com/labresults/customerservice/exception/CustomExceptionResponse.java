package com.labresults.customerservice.exception;

import lombok.Data;

@Data
public class CustomExceptionResponse {
    private String path;
    private String error;
    private String message;
    private int status;
}
