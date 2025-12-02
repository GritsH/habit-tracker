package com.grits.habittracker.repository.habit;

import com.grits.habittracker.entity.habit.HabitFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitFrequencyRepository extends JpaRepository<HabitFrequency, Long> {

    List<HabitFrequency> getByNameContainingIgnoreCase(String name);
}
