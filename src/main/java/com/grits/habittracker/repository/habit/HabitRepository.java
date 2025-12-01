package com.grits.habittracker.repository.habit;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCategory;
import com.grits.habittracker.entity.habit.HabitFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    Optional<Habit> findById(Long habitId);

    List<Habit> findByCreatedAt(Date createdAt);

    List<Habit> findAllByUser_Id(Long userId);

    List<Habit> findAllByUser(User user);

    List<Habit> findAllByHabitCategory_CategoryName(String categoryName);

    List<Habit> findAllByHabitCategory(HabitCategory habitCategory);

    List<Habit> findAllByUser_IdAndCreatedAt(Long userId, Date createdAt);

    List<Habit> findAllByNameContainingIgnoreCase(String name);

    List<Habit> findAllByFrequency_Id(Long frequencyId);

    List<Habit> findAllByFrequency(HabitFrequency frequency);
}
