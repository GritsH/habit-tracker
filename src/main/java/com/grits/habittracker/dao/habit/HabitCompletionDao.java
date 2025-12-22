package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCompletion;
import com.grits.habittracker.exception.HabitAlreadyCompletedException;
import com.grits.habittracker.repository.habit.HabitCompletionRepository;
import com.grits.habittracker.repository.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HabitCompletionDao {

    private final HabitCompletionRepository completionRepository;

    private final HabitRepository habitRepository;

    public HabitCompletion saveCompletion(String habitId, HabitCompletion completion) {
        Habit habit = habitRepository.getReferenceById(habitId);

        completion.setHabit(habit);

        try {
            return completionRepository.save(completion);
        } catch (DataIntegrityViolationException e) {
            throw new HabitAlreadyCompletedException(habitId);
        }
    }

    public List<HabitCompletion> getHabitLogHistory(String habitId) {
        return completionRepository.findAllByHabitId(habitId);
    }
}
