package com.grits.habittracker.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class HabitDto {

    private String id;

    private String name;

    private LocalDate createdAt;

    private String description;

    private UserDto user;

    private String frequency;

    private String habitCategory;

    public static HabitDto fromEntity() {
        return HabitDto.builder().build();
    }
}
