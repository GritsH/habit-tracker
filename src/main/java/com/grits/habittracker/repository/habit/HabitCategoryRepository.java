package com.grits.habittracker.repository.habit;

import com.grits.habittracker.entity.habit.HabitCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitCategoryRepository extends JpaRepository<HabitCategory, String> {

    Optional<HabitCategory> findByName(String name);
}
