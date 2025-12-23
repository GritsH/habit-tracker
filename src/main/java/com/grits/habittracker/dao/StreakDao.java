package com.grits.habittracker.dao;

import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.exception.StreakNotFoundException;
import com.grits.habittracker.repository.StreakRepository;
import com.grits.habittracker.repository.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class StreakDao {

    private final StreakRepository streakRepository;

    private final HabitRepository habitRepository;

    public void save(String habitId) {
        Habit habit = habitRepository.getReferenceById(habitId);
        Streak streak = new Streak();
        streak.setHabit(habit);
        streakRepository.save(streak);
    }

    public void updateStreak(String habitId) {
        Streak streak = streakRepository.findByHabitId(habitId).orElseThrow(() -> new StreakNotFoundException(habitId));
        streak.setLastUpdated(LocalDate.now());
        streak.setCurrentStreak(streak.getCurrentStreak() + 1);
        streak.setLongestStreak(Math.max(streak.getCurrentStreak(), streak.getLongestStreak()));
        streakRepository.save(streak);
    }

    public Streak getStreak(String habitId, String userId) {
        checkHabitOwnership(habitId, userId);
        return streakRepository.findByHabitId(habitId).orElseThrow(() -> new StreakNotFoundException(habitId));
    }

    public void resetStreaks() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        streakRepository
                .findAll()
                .forEach(streak -> {
                    if (wasMissed(streak, yesterday)) {
                        streak.setCurrentStreak(0);
                    }
                });
    }

    private boolean wasMissed(Streak streak, LocalDate yesterday) {
        return streak.getHabit().getFrequency().wasMissed(streak.getLastUpdated(), yesterday);
    }

    private void checkHabitOwnership(String habitId, String userId) {
        if (!habitRepository.existsByIdAndUserId(habitId, userId)) {
            throw new HabitNotFoundException();
        }
    }
}
