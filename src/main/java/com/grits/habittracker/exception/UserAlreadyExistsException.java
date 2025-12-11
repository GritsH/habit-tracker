package com.grits.habittracker.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "User with this email/username already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }
}
