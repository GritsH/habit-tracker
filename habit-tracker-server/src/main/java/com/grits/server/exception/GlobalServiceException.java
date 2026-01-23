package com.grits.server.exception;

import org.springframework.http.HttpStatus;

public class GlobalServiceException extends RuntimeException {

    private final HttpStatus statusCode;
    public GlobalServiceException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
