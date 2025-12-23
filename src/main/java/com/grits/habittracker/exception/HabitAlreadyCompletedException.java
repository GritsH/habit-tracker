package com.grits.habittracker.exception;

public class HabitAlreadyCompletedException extends RuntimeException {

    public HabitAlreadyCompletedException(String habitId) {
        super("Habit " + habitId + " already logged as completed");
    }
}
