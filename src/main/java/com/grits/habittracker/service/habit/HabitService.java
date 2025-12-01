package com.grits.habittracker.service.habit;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCategory;
import com.grits.habittracker.entity.habit.HabitFrequency;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HabitService {

    void save(Habit habit);

    Optional<Habit> findById(Long habitId);

    List<Habit> findByCreatedAt(Date createdAt);

    List<Habit> findAllByUserId(Long userId);

    List<Habit> findAllByUser(User user);

    List<Habit> findAllByHabitCategoryName(String categoryName);

    List<Habit> findAllByHabitCategory(HabitCategory habitCategory);

    List<Habit> findAllByUserIdAndCreatedAt(Long userId, Date createdAt);

    List<Habit> findAllByNameContaining(String name);

    List<Habit> findAllByFrequencyId(Long frequencyId);

    List<Habit> findAllByFrequency(HabitFrequency frequency);
}
