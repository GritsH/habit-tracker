package com.grits.habittracker.model.request;

import lombok.Value;

import java.time.LocalDate;

@Value
public class LogCompletionRequest {

    String habitId;

    LocalDate completedAt;

    boolean isSkipped;
}
