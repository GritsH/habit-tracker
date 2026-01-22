package com.grits.server.exception;

import org.springframework.http.HttpStatus;

public class StreakUpdateFailedException extends GlobalServiceException {
    public StreakUpdateFailedException(String habitId) {
        super("Streak for habit " + habitId + " was not updated", HttpStatus.BAD_REQUEST);
    }
}
