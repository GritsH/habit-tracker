package com.grits.habittracker.repository.habit;

import com.grits.habittracker.entity.habit.Habit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {

    List<Habit> findByCreatedAt(Date createdAt);

    List<Habit> findAllByUserId(Long userId);

    List<Habit> findAllByHabitCategoryName(String categoryName);

    List<Habit> findAllByUserIdAndCreatedAt(Long userId, Date createdAt);

    List<Habit> findAllByNameContaining(String name);

    List<Habit> findAllByFrequencyId(Long frequencyId);
}
