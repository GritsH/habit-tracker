package com.grits.habittracker.repository.habit;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, Long> {

    List<HabitCompletion> getAll();

    List<HabitCompletion> getAllByCompletedAt(Date completedAt);

    List<HabitCompletion> findAllByHabit_Id(Long habitId);

    List<HabitCompletion> findAllByHabit(Habit habit);
}
