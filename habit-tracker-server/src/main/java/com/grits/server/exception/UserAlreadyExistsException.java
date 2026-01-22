package com.grits.server.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends GlobalServiceException {

    private static final String MESSAGE = "User with this email/username already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE, HttpStatus.CONFLICT);
    }
}
