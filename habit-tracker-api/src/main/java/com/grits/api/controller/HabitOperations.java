package com.grits.api.controller;

import com.grits.api.model.request.CreateHabitRequest;
import com.grits.api.model.request.UpdateHabitRequest;
import com.grits.api.model.response.HabitCompletionResponse;
import com.grits.api.model.response.HabitResponse;
import com.grits.api.model.response.StreakResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/users/{userId}/habits")
@Tag(name = "Habit API")
@PreAuthorize("#userId == authentication.principal")
public interface HabitOperations {

    @GetMapping
    @Operation(
            summary = "Get all habits",
            description = "Retrieves a list of habits"
    )
    ResponseEntity<List<HabitResponse>> getAllHabits(@PathVariable String userId);

    @PostMapping
    @Operation(
            summary = "Create a new habit",
            description = "Add a new habit for user"
    )
    ResponseEntity<HabitResponse> createNewHabit(@Valid @RequestBody CreateHabitRequest createHabitRequest, @PathVariable String userId);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remove a habit",
            description = "Deletes the habit if found"
    )
    ResponseEntity<Void> deleteHabit(@PathVariable String userId, @PathVariable String id);

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update a habit",
            description = "Updates a specific habit"
    )
    ResponseEntity<HabitResponse> updateHabit(@PathVariable String userId, @PathVariable String id, @Valid @RequestBody UpdateHabitRequest updateHabitRequest);

    @PostMapping("/{id}/completions")
    @Operation(
            summary = "Log a habit completion",
            description = "Record that the habit was completed"
    )
    ResponseEntity<HabitCompletionResponse> logCompletion(@PathVariable String userId, @PathVariable String id);

    @GetMapping("/{id}/completions")
    @Operation(
            summary = "Get history for a habit",
            description = "Get all completion records for a habit"
    )
    ResponseEntity<List<HabitCompletionResponse>> getHabitLogHistory(@PathVariable String userId, @PathVariable String id);
    @GetMapping("/{id}/streak")
    @Operation(
            summary = "Show a habit streak",
            description = "Shows statistics for habit completions"
    )
    ResponseEntity<StreakResponse> getHabitStreakHistory(@PathVariable String userId, @PathVariable String id);
}
