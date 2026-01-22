package com.grits.api.service;

import com.grits.api.model.response.StreakResponse;

public interface StreakService {

    StreakResponse getStreak(String habitId, String userId);
}
