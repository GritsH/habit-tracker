package com.grits.habittracker.model.request;

import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CreateHabitRequest {

    String name;

    String description;

    LocalDate startDate;

    FrequencyType frequency;

    CategoryType category;
}
