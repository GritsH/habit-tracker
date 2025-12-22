package com.grits.habittracker.repository.habit;

import com.grits.habittracker.entity.habit.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, String> {

    List<HabitCompletion> findAllByHabitId(String habitId);

    boolean existsByCompletionLog(String completionLog);
}
