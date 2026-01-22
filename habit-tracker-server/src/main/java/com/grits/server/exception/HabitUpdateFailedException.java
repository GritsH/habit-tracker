package com.grits.server.exception;

import org.springframework.http.HttpStatus;

public class HabitUpdateFailedException extends GlobalServiceException {

    public HabitUpdateFailedException(String habitId) {
        super("Habit with id " + habitId + " was not updated", HttpStatus.BAD_REQUEST);
    }
}
