package com.grits.habittracker.model.response;

import com.grits.habittracker.model.type.FrequencyType;
import lombok.Value;

import java.time.LocalDate;

@Value
public class HabitResponse {

    String id;

    String name;

    LocalDate createdAt;

    String description;

    UserResponse user;

    FrequencyType frequency;

    String habitCategory;

}
