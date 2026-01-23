package com.grits.server.controller;


import com.grits.api.controller.HabitApi;
import com.grits.api.model.request.CreateHabitRequest;
import com.grits.api.model.request.UpdateHabitRequest;
import com.grits.api.model.response.HabitCompletionResponse;
import com.grits.api.model.response.HabitResponse;
import com.grits.api.model.response.StreakResponse;
import com.grits.server.service.StreakService;
import com.grits.server.service.habit.HabitCompletionService;
import com.grits.server.service.habit.HabitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HabitController implements HabitApi {

    private final HabitService habitService;

    private final HabitCompletionService completionService;

    private final StreakService streakService;

    @Autowired
    public HabitController(HabitService habitService, HabitCompletionService completionService, StreakService streakService) {
        this.habitService = habitService;
        this.completionService = completionService;
        this.streakService = streakService;
    }

    @Override
    public ResponseEntity<List<HabitResponse>> getAllHabits(@PathVariable String userId) {
        return ResponseEntity.ok(habitService.getAllHabits(userId));
    }

    @Override
    public ResponseEntity<HabitResponse> createNewHabit(
            @Valid @RequestBody CreateHabitRequest createHabitRequest,
            @PathVariable String userId
    ) {
        return ResponseEntity.ok(habitService.createNewHabit(userId, createHabitRequest));
    }

    @Override
    public ResponseEntity<Void> deleteHabit(@PathVariable String userId, @PathVariable String id) {
        habitService.deleteHabit(id, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<HabitResponse> updateHabit(
            @PathVariable String userId,
            @PathVariable String id,
            @Valid @RequestBody UpdateHabitRequest updateHabitRequest
    ) {
        return ResponseEntity.ok(habitService.updateHabit(id, updateHabitRequest));
    }

    @Override
    public ResponseEntity<HabitCompletionResponse> logCompletion(@PathVariable String userId, @PathVariable String id) {
        return ResponseEntity.ok(completionService.logCompletion(id, userId));
    }

    @Override
    public ResponseEntity<List<HabitCompletionResponse>> getHabitLogHistory(@PathVariable String userId, @PathVariable String id) {
        return ResponseEntity.ok(completionService.getHabitLogHistory(id, userId));
    }

    @Override
    public ResponseEntity<StreakResponse> getHabitStreakHistory(@PathVariable String userId, @PathVariable String id) {
        return ResponseEntity.ok(streakService.getStreak(id, userId));
    }
}
