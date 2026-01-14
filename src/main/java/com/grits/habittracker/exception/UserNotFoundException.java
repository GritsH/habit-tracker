package com.grits.habittracker.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends GlobalServiceException {

    public UserNotFoundException(String user) {
        super("User " + user + " not found", HttpStatus.NOT_FOUND);
    }
}
