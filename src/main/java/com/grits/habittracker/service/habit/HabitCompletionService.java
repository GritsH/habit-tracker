package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.StreakDao;
import com.grits.habittracker.dao.habit.HabitCompletionDao;
import com.grits.habittracker.entity.habit.HabitCompletion;
import com.grits.habittracker.mapper.HabitCompletionMapper;
import com.grits.habittracker.model.response.HabitCompletionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitCompletionService {

    private final HabitCompletionDao completionDao;

    private final StreakDao streakDao;

    private final HabitCompletionMapper completionMapper;

    @Transactional
    public HabitCompletionResponse logCompletion(String habitId, String userId) {
        log.info("Logging completion for habit: {}", habitId);
        HabitCompletion habitCompletion = completionMapper.toEntity(habitId);
        HabitCompletionResponse response = completionMapper.toResponse(
                completionDao.saveCompletion(habitId, userId, habitCompletion)
        );
        streakDao.updateStreakContinuation(habitId);
        log.info("Habit {} logged successfully", habitId);
        return response;
    }

    public List<HabitCompletionResponse> getHabitLogHistory(String habitId, String userId) {
        log.info("Retrieving completions log for habit: {}", habitId);
        List<HabitCompletion> history = completionDao.getHabitLogHistory(habitId, userId);
        log.info("Completion history for habit {} retrieved successfully", habitId);
        return completionMapper.toDtoList(history);
    }
}
