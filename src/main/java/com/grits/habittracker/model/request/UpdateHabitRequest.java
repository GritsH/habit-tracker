package com.grits.habittracker.model.request;

import lombok.Value;

import java.time.LocalDate;

@Value
public class UpdateHabitRequest {

    String name;

    String description;

    LocalDate startDate;

    String frequency;

    String habitCategory;
}
