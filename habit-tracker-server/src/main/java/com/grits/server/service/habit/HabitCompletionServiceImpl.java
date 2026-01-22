package com.grits.server.service.habit;

import com.grits.api.service.habit.HabitCompletionService;
import com.grits.server.dao.StreakDao;
import com.grits.server.dao.habit.HabitCompletionDao;
import com.grits.server.entity.habit.HabitCompletion;
import com.grits.server.mapper.HabitCompletionMapper;
import com.grits.api.model.response.HabitCompletionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class HabitCompletionServiceImpl implements HabitCompletionService {

    private final HabitCompletionDao completionDao;

    private final StreakDao streakDao;

    private final HabitCompletionMapper completionMapper;

    @Override
    public HabitCompletionResponse logCompletion(String habitId, String userId) {
        log.info("Logging completion for habit: {}", habitId);
        HabitCompletion habitCompletion = completionMapper.toEntity(habitId);
        HabitCompletionResponse response = completionMapper.toResponse(
                completionDao.saveCompletion(habitId, userId, habitCompletion)
        );
        streakDao.incrementStreak(habitId);
        log.info("Habit {} logged successfully", habitId);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitCompletionResponse> getHabitLogHistory(String habitId, String userId) {
        log.info("Retrieving completions log for habit: {}", habitId);
        List<HabitCompletion> history = completionDao.getHabitLogHistory(habitId, userId);
        log.info("Completion history for habit {} retrieved successfully", habitId);
        return completionMapper.toDtoList(history);
    }
}
