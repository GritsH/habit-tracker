package com.grits.api.service.habit;

import com.grits.api.model.request.CreateHabitRequest;
import com.grits.api.model.request.UpdateHabitRequest;
import com.grits.api.model.response.HabitResponse;

import java.util.List;

public interface HabitService {

    HabitResponse createNewHabit(String userId, CreateHabitRequest createHabitRequest);

    List<HabitResponse> getAllHabits(String userId);

    void deleteHabit(String habitId, String userId);

    HabitResponse updateHabit(String habitId, UpdateHabitRequest updateHabitRequest);
}
