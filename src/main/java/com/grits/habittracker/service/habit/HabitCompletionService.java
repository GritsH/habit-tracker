package com.grits.habittracker.service.habit;

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
@Transactional
public class HabitCompletionService {

    private final HabitCompletionDao completionDao;

    private final HabitCompletionMapper completionMapper;

    public HabitCompletionResponse logCompletion(String habitId) {
        log.info("Logging completion for habit: {}", habitId);

        HabitCompletion habitCompletion = completionMapper.toEntity(habitId);

        HabitCompletionResponse response = completionMapper.toResponse(
                completionDao.saveCompletion(habitId, habitCompletion)
        );

        //todo update streak

        log.info("Habit {} logged successfully", habitId);
        return response;
    }

    public List<HabitCompletionResponse> getHabitLogHistory(String habitId) {
        log.info("Retrieving completions log for habit: {}", habitId);

        List<HabitCompletion> history = completionDao.getHabitLogHistory(habitId);

        log.info("Completion history for habit {} retrieved successfully", habitId);
        return completionMapper.toDtoList(history);
    }
}
