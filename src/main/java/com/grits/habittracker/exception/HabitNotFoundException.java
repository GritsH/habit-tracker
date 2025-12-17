package com.grits.habittracker.exception;

public class HabitNotFoundException extends RuntimeException {

    public HabitNotFoundException(String habitId) {
        super("Habit with id " + habitId + " not found");
    }
}
