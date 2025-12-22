package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCompletion;
import com.grits.habittracker.exception.HabitAlreadyCompletedException;
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

    public HabitCompletion saveCompletion(String habitId, HabitCompletion completion) {
        String completionLog = habitId + "_" + LocalDate.now();
        if (completionRepository.existsByCompletionLog(completionLog)) {
            throw new HabitAlreadyCompletedException(habitId);
        }

        Habit habit = habitRepository.getReferenceById(habitId);
        completion.setCompletionLog(completionLog);
        completion.setHabit(habit);
        return completionRepository.save(completion);
    }

    public List<HabitCompletion> getHabitLogHistory(String habitId) {
        return completionRepository.findAllByHabitId(habitId);
    }
}
