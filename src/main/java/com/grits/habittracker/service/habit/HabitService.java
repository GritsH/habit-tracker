package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.habit.HabitDao;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.mapper.HabitMapper;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitService {

    private final HabitDao habitDao;

    private final HabitFrequencyService frequencyService;

    private final HabitCategoryService categoryService;

    private final UserService userService;

    private final HabitMapper habitMapper;

    public void createNewHabit(CreateHabitRequest createHabitRequest) {
        log.info("Creating a new habit: {}", createHabitRequest.getName());

        Habit newHabit = habitMapper.createDtoToEntity(createHabitRequest);
        //todo set user somehow
        newHabit.setHabitCategory(categoryService.getByName(createHabitRequest.getHabitCategory()));
        newHabit.setFrequency(frequencyService.getByName(createHabitRequest.getFrequency()));
        newHabit.setCreatedAt(LocalDate.now());

        habitDao.saveHabit(newHabit);

        log.info("New habit {} created successfully", newHabit.getName());
    }
}
