package com.grits.habittracker.model;

import com.grits.habittracker.entity.Streak;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StreakDto {

    private Integer currentStreak;

    private Integer longestStreak;

    private HabitDto habit;

    public static StreakDto fromEntity(Streak entity) {
        return StreakDto.builder()
                .currentStreak(entity.getCurrentStreak())
                .longestStreak(entity.getLongestStreak())
                .habit(HabitDto.fromEntity(entity.getHabit()))
                .build();
    }
}
