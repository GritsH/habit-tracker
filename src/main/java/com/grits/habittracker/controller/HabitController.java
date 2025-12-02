package com.grits.habittracker.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/habits")
public class HabitController {
    //todo return ResponseEntity<>?

    @GetMapping
    public void getAllHabits() {

    }

    @PostMapping
    public void createNewHabit() {

    }

    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable Long id) {

    }

    @PutMapping("/{id}")
    public void updateHabit(@PathVariable Long id) {

    }

    @PostMapping("/{id}/completions")
    public void logCompletion(@PathVariable Long id) {

    }

    @GetMapping("/{id}/completions")
    public void getHabitLogHistory(@PathVariable Long id) {

    }

    @GetMapping("/{id}/streak")
    public void getHabitStreakHistory(@PathVariable Long id) {

    }
}
