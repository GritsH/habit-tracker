package com.grits.habittracker.dao;

import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.exception.StreakNotFoundException;
import com.grits.habittracker.exception.StreakUpdateFailedException;
import com.grits.habittracker.model.type.FrequencyType;
import com.grits.habittracker.repository.StreakRepository;
import com.grits.habittracker.repository.habit.HabitRepository;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class StreakDao {

    private final StreakRepository streakRepository;

    private final HabitRepository habitRepository;

    public void save(String habitId, FrequencyType frequency) {
        Streak streak = new Streak();
        streak.setFrequency(frequency);
        streak.setHabitId(habitId);
        streakRepository.save(streak);
    }

    public void updateStreak(String habitId, FrequencyType frequency) {
        if (isNotEmpty(frequency)) {
            Streak streak = streakRepository.findByHabitId(habitId).orElseThrow(() -> new StreakNotFoundException(habitId));
            streak.setFrequency(frequency);
            streakRepository.save(streak);
        }
    }

    public void incrementStreak(String habitId) {
        Streak streak;
        try {
            streak = streakRepository.findByHabitIdWithLock(habitId).orElseThrow(() -> new StreakNotFoundException(habitId));
        } catch (PessimisticLockException e) {
            throw new StreakUpdateFailedException(habitId);
        }
        if (isNotEmpty(streak.getResetAt()) && streak.getResetAt().isBefore(LocalDate.now())) {
            streak.setCurrentStreak(1);
        } else {
            streak.setCurrentStreak(streak.getCurrentStreak() + 1);
        }
        streak.setResetAt(streak.getFrequency().calculateResetAt());
        streak.setLongestStreak(Math.max(streak.getCurrentStreak(), streak.getLongestStreak()));
        streakRepository.save(streak);
    }

    public void resetAllMissedStreaks() {
        int batchUpdated;
        do {
            batchUpdated = streakRepository.resetMissedStreaks(1000);
        } while (batchUpdated > 0);
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
