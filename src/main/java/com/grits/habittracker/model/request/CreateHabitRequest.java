package com.grits.habittracker.model.request;

import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.LocalDate;

@Value
public class CreateHabitRequest {

    @NotBlank
    @Size(min = 2, max = 30, message = "Name must be 2-30 characters long")
    String name;

    String description;

    @FutureOrPresent
    LocalDate startDate;

    @NotNull
    FrequencyType frequency;

    @NotNull
    CategoryType category;
}
