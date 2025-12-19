package com.grits.habittracker.model.response;

import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
import lombok.Value;

import java.time.LocalDate;

@Value
public class HabitResponse {

    String id;

    Long version;

    String name;

    LocalDate createdAt;

    LocalDate startDate;

    String description;

    FrequencyType frequency;

    CategoryType category;

}
