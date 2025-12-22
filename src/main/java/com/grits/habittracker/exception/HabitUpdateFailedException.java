package com.grits.habittracker.exception;

public class HabitUpdateFailedException extends RuntimeException {

    public HabitUpdateFailedException(String habitId) {
        super("Habit with id " + habitId + " was not updated");
    }
}
