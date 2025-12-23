package com.grits.habittracker.repository;

import com.grits.habittracker.entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreakRepository extends JpaRepository<Streak, String> {

    Optional<Streak> findByHabitId(String id);

    void deleteAllByHabitId(String habitId);
}
