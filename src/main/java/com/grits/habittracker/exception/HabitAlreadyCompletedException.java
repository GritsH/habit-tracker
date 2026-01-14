package com.grits.habittracker.exception;

import org.springframework.http.HttpStatus;

public class HabitAlreadyCompletedException extends GlobalServiceException {

    public HabitAlreadyCompletedException(String habitId) {
        super("Habit " + habitId + " already logged as completed", HttpStatus.CONFLICT);
    }
}
