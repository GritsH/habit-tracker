package com.grits.habittracker.repository.habit;

import com.grits.habittracker.entity.habit.HabitFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitFrequencyRepository extends JpaRepository<HabitFrequency, String> {
}
