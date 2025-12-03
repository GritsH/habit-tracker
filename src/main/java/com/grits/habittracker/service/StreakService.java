package com.grits.habittracker.service;

import com.grits.habittracker.entity.Streak;

import java.util.List;
import java.util.Optional;

public interface StreakService {

    void save(Streak streak);

    List<Streak> getAll();

    Optional<Streak> findById(Long id);

    Optional<Streak> findByHabitId(Long id);
}
