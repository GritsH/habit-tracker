package com.grits.server.repository.habit;

import com.grits.server.entity.habit.HabitCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitCompletionRepository extends JpaRepository<HabitCompletion, String> {

    List<HabitCompletion> findAllByHabitId(String habitId);

    void deleteAllByHabitId(String habitId);
}
