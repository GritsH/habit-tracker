package com.grits.habittracker.service;

import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.entity.habit.Habit;

import java.util.List;
import java.util.Optional;

public interface StreakService {

    void save(Streak streak);

    List<Streak> getAll();

    Optional<Streak> findById(Long id);

    Optional<Streak> findByHabit(Habit habit);

    Optional<Streak> findByHabitId(Long id);
}
