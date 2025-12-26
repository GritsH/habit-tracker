package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.HabitCompletion;
import com.grits.habittracker.exception.HabitAlreadyCompletedException;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.repository.habit.HabitCompletionRepository;
import com.grits.habittracker.repository.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HabitCompletionDao {

    private final HabitCompletionRepository completionRepository;

    private final HabitRepository habitRepository;

    public HabitCompletion saveCompletion(String habitId, String userId, HabitCompletion completion) {
        checkHabitOwnership(habitId, userId);
        String completionLog = habitId + "_" + LocalDate.now();
        if (completionRepository.existsByCompletionLog(completionLog)) {
            throw new HabitAlreadyCompletedException(habitId);
        }
        completion.setCompletionLog(completionLog);
        return completionRepository.save(completion);
    }

    public List<HabitCompletion> getHabitLogHistory(String habitId, String userId) {
        checkHabitOwnership(habitId, userId);
        return completionRepository.findAllByHabitId(habitId);
    }

    private void checkHabitOwnership(String habitId, String userId) {
        if (!habitRepository.existsByIdAndUserId(habitId, userId)) {
            throw new HabitNotFoundException();
        }
    }
}
