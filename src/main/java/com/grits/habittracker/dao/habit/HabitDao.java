package com.grits.habittracker.dao.habit;

import com.grits.habittracker.dao.UserDao;
import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCategory;
import com.grits.habittracker.entity.habit.HabitFrequency;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.repository.habit.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class HabitDao {

    private final HabitRepository habitRepository;

    private final UserDao userDao;

    private final HabitFrequencyDao habitFrequencyDao;

    private final HabitCategoryDao habitCategoryDao;

    @Autowired
    public HabitDao(HabitRepository habitRepository, UserDao userDao, HabitFrequencyDao habitFrequencyDao, HabitCategoryDao habitCategoryDao) {
        this.habitRepository = habitRepository;
        this.userDao = userDao;
        this.habitFrequencyDao = habitFrequencyDao;
        this.habitCategoryDao = habitCategoryDao;
    }

    public void saveHabit(CreateHabitRequest createHabitRequest, String username) {
        User user = userDao.getUserByUsername(username);

        HabitFrequency frequency = habitFrequencyDao.getByName(createHabitRequest.getFrequency());
        HabitCategory category = habitCategoryDao.getByName(createHabitRequest.getHabitCategory());

        Habit habit = new Habit();
        habit.setUser(user);
        habit.setName(createHabitRequest.getName());
        habit.setDescription(createHabitRequest.getDescription());
        habit.setCreatedAt(LocalDate.now());
        habit.setStartDate(createHabitRequest.getStartDate());
        habit.setHabitCategory(category);
        habit.setFrequency(frequency);

        habitRepository.save(habit);
    }

    public void deleteHabit(String habitId) {
        habitRepository.deleteById(habitId);
    }

    public Habit updateHabit(UpdateHabitRequest updateHabitRequest, String habitId) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new HabitNotFoundException(habitId));

        HabitFrequency frequency = habitFrequencyDao.getByName(updateHabitRequest.getFrequency());
        HabitCategory category = habitCategoryDao.getByName(updateHabitRequest.getHabitCategory());

        habit.setName(updateHabitRequest.getName());
        habit.setDescription(updateHabitRequest.getDescription());
        habit.setHabitCategory(category);
        habit.setFrequency(frequency);
        habit.setStartDate(updateHabitRequest.getStartDate());

        return habitRepository.save(habit);
    }

    public List<Habit> getUserHabits(String username) {
        User user = userDao.getUserByUsername(username);
        return habitRepository.findAllByUserId(user.getId());
    }
}
