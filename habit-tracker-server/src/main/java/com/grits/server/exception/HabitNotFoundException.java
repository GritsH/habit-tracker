package com.grits.server.exception;

import org.springframework.http.HttpStatus;

public class HabitNotFoundException extends GlobalServiceException {

    public HabitNotFoundException(String habitId) {
        super("Habit with id " + habitId + " not found", HttpStatus.NOT_FOUND);
    }

    public HabitNotFoundException() {
        super("No information could be retrieved", HttpStatus.NOT_FOUND);
    }
}
