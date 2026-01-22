package com.grits.api.model.response;

import com.grits.api.model.type.FrequencyType;
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
