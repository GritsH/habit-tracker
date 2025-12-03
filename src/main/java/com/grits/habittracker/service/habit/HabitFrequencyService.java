package com.grits.habittracker.service.habit;

import com.grits.habittracker.entity.habit.HabitFrequency;

import java.util.List;

public interface HabitFrequencyService {

    List<HabitFrequency> getAll();

    List<HabitFrequency> getByNameContaining(String name);
}
