package com.grits.habittracker.service.habit;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCompletion;

import java.util.Date;
import java.util.List;

public interface HabitCompletionService {

    void save(HabitCompletion habitCompletion);

    List<HabitCompletion> getAll();

    List<HabitCompletion> getAllByCompletedAt(Date completedAt);

    List<HabitCompletion> findAllByHabitId(Long habitId);

    List<HabitCompletion> findAllByHabit(Habit habit);
}
