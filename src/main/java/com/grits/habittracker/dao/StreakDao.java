package com.grits.habittracker.dao;

import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.exception.StreakNotFoundException;
import com.grits.habittracker.repository.StreakRepository;
import com.grits.habittracker.repository.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreakDao {

    private final StreakRepository streakRepository;

    private final HabitRepository habitRepository;

    public void save(Streak streak) {
        streakRepository.save(streak);
    }

    public void updateStreak(String habitId) {
        Streak streak = streakRepository.findByHabitId(habitId).orElseThrow(() -> new StreakNotFoundException(habitId));
        streak.setCurrentStreak(streak.getCurrentStreak() + 1);
        streak.setLongestStreak(Math.max(streak.getCurrentStreak(), streak.getLongestStreak()));
        streakRepository.save(streak);
    }

    public Streak getStreak(String habitId, String userId) {
        checkHabitOwnership(habitId, userId);
        return streakRepository.findByHabitId(habitId).orElseThrow(() -> new StreakNotFoundException(habitId));
    }

    private void checkHabitOwnership(String habitId, String userId) {
        if (!habitRepository.existsByIdAndUserId(habitId, userId)) {
            throw new HabitNotFoundException();
        }
    }
}
