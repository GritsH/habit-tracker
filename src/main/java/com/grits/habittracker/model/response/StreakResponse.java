package com.grits.habittracker.model.response;

import lombok.Value;

import java.time.LocalDate;

@Value
public class StreakResponse {

    Integer currentStreak;

    Integer longestStreak;

    String habitId;

    LocalDate lastUpdated;
}
