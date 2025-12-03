package com.grits.habittracker.controller;


import com.grits.habittracker.model.HabitDto;
import com.grits.habittracker.model.HabitCompletionDto;
import com.grits.habittracker.model.StreakDto;
import com.grits.habittracker.model.request.HabitRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/habits")
@Tag(name = "Habit API")
public class HabitController {

    @GetMapping
    @Operation(
            summary = "Get all habits",
            description = "Retrieves a list of habits"
    )
    public ResponseEntity<List<HabitDto>> getAllHabits() { //todo return Habit entity
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping
    @Operation(
            summary = "Create a new habit",
            description = "Add a new habit for user"
    )
    public ResponseEntity<Void> createNewHabit(@RequestBody HabitRequestDto habitDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remove a habit",
            description = "Deletes the habit if found"
    )
    public ResponseEntity<Void> deleteHabit(@PathVariable UUID id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a habit",
            description = "Updates a specific habit"
    )
    public ResponseEntity<Void> updateHabit(@PathVariable UUID id, @RequestBody HabitRequestDto habitDto) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/completions")
    @Operation(
            summary = "Log a habit completion",
            description = "Record that the habit was completed"
    )
    public ResponseEntity<Void> logCompletion(@PathVariable UUID id, @RequestBody HabitCompletionDto habitCompletionDto) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}/completions")
    @Operation(
            summary = "Get history for a habit",
            description = "Get all completion records for a habit"
    )
    public ResponseEntity<List<HabitCompletionDto>> getHabitLogHistory(@PathVariable UUID id) { //todo return HabitCompletion entity
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{id}/streak")
    @Operation(
            summary = "Show a habit streak",
            description = "Shows statistics for habit completions"
    )
    public ResponseEntity<StreakDto> getHabitStreakHistory(@PathVariable UUID id) { //todo return Streak entity
        return ResponseEntity.ok(new StreakDto());
    }
}
