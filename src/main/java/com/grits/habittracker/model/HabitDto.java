package com.grits.habittracker.model;

import com.grits.habittracker.entity.habit.Habit;
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

    public static HabitDto fromEntity(Habit entity) {
        return HabitDto.builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .description(entity.getDescription())
                .frequency(entity.getFrequency().getName())
                .habitCategory(entity.getHabitCategory().getName())
                .user(UserDto.fromEntity(entity.getUser()))
                .build();
    }
}
