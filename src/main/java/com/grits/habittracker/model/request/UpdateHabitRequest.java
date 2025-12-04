package com.grits.habittracker.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHabitRequest {

    private String name;

    private String description;

    private String frequency;

    private String habitCategory;
}
