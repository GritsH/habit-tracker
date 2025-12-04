package com.grits.habittracker.model;

import com.grits.habittracker.entity.habit.HabitCompletion;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class HabitCompletionDto {

    private HabitDto habit;

    private LocalDate completedAt;

    private boolean isSkipped;

    public static HabitCompletionDto fromEntity(HabitCompletion entity) {
        return HabitCompletionDto.builder()
                .completedAt(entity.getCompletedAt())
                .isSkipped(entity.isSkipped())
                .habit(HabitDto.fromEntity(entity.getHabit()))
                .build();
    }

}
