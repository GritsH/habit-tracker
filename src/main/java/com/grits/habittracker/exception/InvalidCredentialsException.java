package com.grits.habittracker.exception;

public class InvalidCredentialsException extends RuntimeException {

    private static final String MESSAGE = "Invalid credentials";

    public InvalidCredentialsException() {
        super(MESSAGE);
    }
}
