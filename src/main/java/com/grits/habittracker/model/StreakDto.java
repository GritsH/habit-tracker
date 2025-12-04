package com.grits.habittracker.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StreakDto {

    private Integer currentStreak;

    private Integer longestStreak;

    private HabitDto habit;

    public static StreakDto fromEntity() {
        return StreakDto.builder().build();
    }
}
