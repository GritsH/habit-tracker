package com.grits.habittracker.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends GlobalServiceException {

    private static final String MESSAGE = "Invalid credentials";

    public InvalidCredentialsException() {
        super(MESSAGE, HttpStatus.UNAUTHORIZED);
    }

    public InvalidCredentialsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
