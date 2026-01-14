package com.grits.habittracker.model.response;

import com.grits.habittracker.model.type.FrequencyType;
import lombok.Value;

import java.time.LocalDate;

@Value
public class StreakResponse {

    Integer currentStreak;

    Integer longestStreak;

    String habitId;

    LocalDate resetAt;

    FrequencyType frequency;
}
