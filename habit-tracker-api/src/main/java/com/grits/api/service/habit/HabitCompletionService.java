package com.grits.api.service.habit;

import com.grits.api.model.response.HabitCompletionResponse;

import java.util.List;

public interface HabitCompletionService {

    HabitCompletionResponse logCompletion(String habitId, String userId);

    List<HabitCompletionResponse> getHabitLogHistory(String habitId, String userId);
}
