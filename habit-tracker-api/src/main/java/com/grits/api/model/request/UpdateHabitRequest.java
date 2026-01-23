package com.grits.api.model.request;

import com.grits.api.model.type.CategoryType;
import com.grits.api.model.type.FrequencyType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UpdateHabitRequest {

    @NotBlank
    String name;

    @NotBlank
    String description;

    @FutureOrPresent
    LocalDate startDate;

    @NotNull
    FrequencyType frequency;

    @NotNull
    CategoryType category;
}
