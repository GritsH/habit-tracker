package com.grits.server.service.habit;

import com.grits.api.model.request.CreateHabitRequest;
import com.grits.api.model.request.UpdateHabitRequest;
import com.grits.api.model.response.HabitResponse;
import com.grits.server.dao.StreakDao;
import com.grits.server.dao.habit.HabitDao;
import com.grits.server.entity.habit.Habit;
import com.grits.server.mapper.HabitMapper;
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
public class HabitService {

    private final HabitDao habitDao;

    private final StreakDao streakDao;

    private final HabitMapper habitMapper;

    public HabitResponse createNewHabit(String userId, CreateHabitRequest createHabitRequest) {
        log.info("Saving new habit for user {}", userId);
        Habit habit = habitMapper.toHabit(createHabitRequest);
        HabitResponse response = habitMapper.toDto(habitDao.saveHabit(habit, userId));
        streakDao.save(response.getId(), createHabitRequest.getFrequency());
        log.info("New habit saved successfully");
        return response;
    }

    @Transactional(readOnly = true)
    public List<HabitResponse> getAllHabits(String userId) {
        log.info("Retrieving habits for user {}", userId);
        List<Habit> userHabits = habitDao.getUserHabits(userId);
        log.info("Habits for user {} retrieved successfully", userId);
        return habitMapper.toDtoList(userHabits);
    }

    @Transactional(readOnly = true)
    public HabitResponse getHabit(String habitId, String userId) {
        log.info("Retrieving habit {} for user {}", habitId, userId);
        Habit userHabit = habitDao.getHabitById(userId, habitId);
        log.info("Habit {} for user {} retrieved successfully", habitId, userId);
        return habitMapper.toDto(userHabit);
    }

    public void deleteHabit(String habitId, String userId) {
        log.info("Delete attempt for a habit with id: {}", habitId);
        habitDao.deleteHabit(habitId, userId);
        log.info("Habit with id {} deleted successfully", habitId);
    }

    public HabitResponse updateHabit(String userId, String habitId, UpdateHabitRequest updateHabitRequest) {
        log.info("Updating habit with id: {}", habitId);
        Habit habit = habitDao.getHabitById(userId, habitId);
        habitMapper.updateHabit(updateHabitRequest, habit);
        habitDao.updateHabit(habit, userId);
        streakDao.updateStreak(habitId, updateHabitRequest.getFrequency());
        log.info("Habit {} updated successfully", habitId);
        return habitMapper.toDto(habit);
    }
}
