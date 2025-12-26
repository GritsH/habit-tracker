package com.grits.habittracker.exception;

import org.springframework.http.HttpStatus;

public class StreakNotFoundException extends GlobalServiceException {

    public StreakNotFoundException(String habitId) {
        super("Streak for a habit " + habitId + " not found", HttpStatus.NOT_FOUND);
    }
}
