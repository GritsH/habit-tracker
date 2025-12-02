package com.grits.habittracker.repository;

import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.entity.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Long> {

    Optional<Streak> findById(Long id);

    Optional<Streak> findByHabit(Habit habit);

    Optional<Streak> findByHabit_Id(Long id);
}
