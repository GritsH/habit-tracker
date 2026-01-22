package com.grits.api.model.response;

import lombok.Value;

import java.time.LocalDate;

@Value
public class HabitCompletionResponse {

    String id;

    LocalDate loggedAt;

    String completionLog;
}
