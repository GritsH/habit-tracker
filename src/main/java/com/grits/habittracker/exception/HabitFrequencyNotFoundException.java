package com.grits.habittracker.exception;

public class HabitFrequencyNotFoundException extends RuntimeException {

    public HabitFrequencyNotFoundException(String frequency) {
        super("Habit frequency " + frequency + " not found");
    }

}
