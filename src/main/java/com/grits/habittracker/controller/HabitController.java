package com.grits.habittracker.controller;


import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.model.HabitCompletionDto;
import com.grits.habittracker.model.HabitDto;
import com.grits.habittracker.model.StreakDto;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.LogCompletionRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/v1/habits")
@Tag(name = "Habit API")
public class HabitController {

    @GetMapping
    @Operation(
            summary = "Get all habits",
            description = "Retrieves a list of habits"
    )
    public ResponseEntity<List<HabitDto>> getAllHabits() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping
    @Operation(
            summary = "Create a new habit",
            description = "Add a new habit for user"
    )
    public ResponseEntity<Void> createNewHabit(@RequestBody CreateHabitRequest createHabitRequest) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remove a habit",
            description = "Deletes the habit if found"
    )
    public ResponseEntity<Void> deleteHabit(@PathVariable String id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update a habit",
            description = "Updates a specific habit"
    )
    public ResponseEntity<Void> updateHabit(@PathVariable String id, @RequestBody UpdateHabitRequest updateHabitRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/completions")
    @Operation(
            summary = "Log a habit completion",
            description = "Record that the habit was completed"
    )
    public ResponseEntity<Void> logCompletion(@PathVariable String id, @RequestBody LogCompletionRequest logCompletionRequest) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/completions")
    @Operation(
            summary = "Get history for a habit",
            description = "Get all completion records for a habit"
    )
    public ResponseEntity<List<HabitCompletionDto>> getHabitLogHistory(@PathVariable String id) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{id}/streak")
    @Operation(
            summary = "Show a habit streak",
            description = "Shows statistics for habit completions"
    )
    public ResponseEntity<StreakDto> getHabitStreakHistory(@PathVariable String id) {
        return ResponseEntity.ok(StreakDto.fromEntity(new Streak()));
    }
}
