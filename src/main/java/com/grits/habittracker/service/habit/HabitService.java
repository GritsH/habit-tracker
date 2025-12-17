package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.habit.HabitDao;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.mapper.HabitMapper;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.model.response.HabitResponse;
import com.grits.habittracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitService {

    private final HabitDao habitDao;

    private final UserService userService;

    private final HabitMapper habitMapper;

    public void createNewHabit(String username, CreateHabitRequest createHabitRequest) {
        log.info("Saving new habit for user {}", username);

        habitDao.saveHabit(createHabitRequest, username);

        log.info("New habit saved successfully");
    }

    public List<HabitResponse> getAllHabits(String username) {
        log.info("Retrieving habits for user {}", username);

        List<Habit> userHabits = habitDao.getUserHabits(username);

        return habitMapper.entityListToDtoList(userHabits);
    }

    public void deleteHabit(String habitId) {
        log.info("Delete attempt for a habit with id: {}", habitId);

        habitDao.deleteHabit(habitId);

        log.info("Habit with id {} deleted successfully", habitId);
    }

    public HabitResponse updateHabit(String habitId, UpdateHabitRequest updateHabitRequest) {
        log.info("Updating habit with id: {}", habitId);

        Habit habit = habitDao.updateHabit(updateHabitRequest, habitId);

        log.info("Habit {} updated successfully", habitId);
        return habitMapper.entityToDto(habit);
    }
}
