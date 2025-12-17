package com.grits.habittracker.controller;


import com.grits.habittracker.model.response.HabitCompletionResponse;
import com.grits.habittracker.model.response.HabitResponse;
import com.grits.habittracker.model.response.StreakResponse;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.LogCompletionRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.service.habit.HabitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/users/{username}/habits")
@Tag(name = "Habit API")
public class HabitController {

    private final HabitService habitService;

    @Autowired
    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @GetMapping
    @Operation(
            summary = "Get all habits",
            description = "Retrieves a list of habits"
    )
    public ResponseEntity<List<HabitResponse>> getAllHabits(@PathVariable String username) {
        return ResponseEntity.ok(habitService.getAllHabits(username));
    }

    @PostMapping
    @Operation(
            summary = "Create a new habit",
            description = "Add a new habit for user"
    )
    public ResponseEntity<Void> createNewHabit(@RequestBody CreateHabitRequest createHabitRequest, @PathVariable String username) {
        habitService.createNewHabit(username, createHabitRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remove a habit",
            description = "Deletes the habit if found"
    )
    public ResponseEntity<Void> deleteHabit(@PathVariable String username, @PathVariable String id) {
        habitService.deleteHabit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update a habit",
            description = "Updates a specific habit"
    )
    public ResponseEntity<HabitResponse> updateHabit(@PathVariable String username, @PathVariable String id, @RequestBody UpdateHabitRequest updateHabitRequest) {
        return ResponseEntity.ok(habitService.updateHabit(id, updateHabitRequest));
    }

    @PostMapping("/{id}/completions")
    @Operation(
            summary = "Log a habit completion",
            description = "Record that the habit was completed"
    )
    public ResponseEntity<Void> logCompletion(@PathVariable String username, @PathVariable String id, @RequestBody LogCompletionRequest logCompletionRequest) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/completions")
    @Operation(
            summary = "Get history for a habit",
            description = "Get all completion records for a habit"
    )
    public ResponseEntity<List<HabitCompletionResponse>> getHabitLogHistory(@PathVariable String username, @PathVariable String id) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{id}/streak")
    @Operation(
            summary = "Show a habit streak",
            description = "Shows statistics for habit completions"
    )
    public ResponseEntity<StreakResponse> getHabitStreakHistory(@PathVariable String username, @PathVariable String id) {
        return null; // ResponseEntity.ok(new Object());
    }
}
