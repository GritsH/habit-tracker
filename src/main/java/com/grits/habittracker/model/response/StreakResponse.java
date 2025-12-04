package com.grits.habittracker.model.response;

import lombok.Value;

@Value
public class StreakResponse {

    Integer currentStreak;

    Integer longestStreak;

    HabitResponse habit;
}
