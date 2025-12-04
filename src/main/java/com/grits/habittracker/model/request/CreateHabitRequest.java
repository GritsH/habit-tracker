package com.grits.habittracker.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateHabitRequest {

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String frequency;

    @NotBlank
    private String habitCategory;
}
