package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.habit.HabitDao;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.mapper.HabitMapper;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.model.response.HabitResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class HabitService {

    private final HabitDao habitDao;

    private final HabitMapper habitMapper;

    public HabitResponse createNewHabit(String userId, CreateHabitRequest createHabitRequest) {
        log.info("Saving new habit for user {}", userId);

        Habit habit = habitMapper.toHabit(createHabitRequest);

        HabitResponse response = habitMapper.toDto(habitDao.saveHabit(habit, userId));

        log.info("New habit saved successfully");

        return response;
    }

    public List<HabitResponse> getAllHabits(String userId) {
        log.info("Retrieving habits for user {}", userId);

        List<Habit> userHabits = habitDao.getUserHabits(userId);

        log.info("Habits for user {} retrieved successfully", userId);

        return habitMapper.toDtoList(userHabits);
    }

    public void deleteHabit(String habitId) {
        log.info("Delete attempt for a habit with id: {}", habitId);

        habitDao.deleteHabit(habitId);

        log.info("Habit with id {} deleted successfully", habitId);
    }

    public HabitResponse updateHabit(String habitId, UpdateHabitRequest updateHabitRequest) {
        log.info("Updating habit with id: {}", habitId);

        Habit habit = habitDao.getHabitById(habitId);

        habitMapper.updateHabit(updateHabitRequest, habit);

        try{
            habitDao.saveUpdatedHabit(habit);
            log.info("Habit {} updated successfully", habitId);
            return habitMapper.toDto(habit);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Habit was not updated. Try again later");
        }
    }
}
