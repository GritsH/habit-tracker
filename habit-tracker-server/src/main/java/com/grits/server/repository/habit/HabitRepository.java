package com.grits.server.repository.habit;

import com.grits.server.entity.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, String> {

    List<Habit> findAllByUserId(String userId);

    boolean existsByIdAndUserId(String habitId, String userId);
}
