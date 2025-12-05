package com.grits.habittracker.model.response;

import lombok.Value;

import java.time.LocalDate;

@Value
public class HabitCompletionResponse {

    HabitResponse habit;

    LocalDate completedAt;

    boolean isSkipped;
}
