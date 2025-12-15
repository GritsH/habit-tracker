package com.grits.habittracker.exception;

public class HabitCategoryNotFoundException extends RuntimeException {

    public HabitCategoryNotFoundException(String category) {
        super("Habit category " + category + " not found");
    }
}
