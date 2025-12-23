package com.grits.habittracker.exception;

public class StreakNotFoundException extends RuntimeException {

    public StreakNotFoundException(String habitId) {
        super("Streak for a habit " + habitId + " not found");
    }
}
