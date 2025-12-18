package com.grits.habittracker.dao.habit;

import com.grits.habittracker.dao.UserDao;
import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.repository.habit.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitDao {

    private final HabitRepository habitRepository;

    private final UserDao userDao;

    @Autowired
    public HabitDao(HabitRepository habitRepository, UserDao userDao) {
        this.habitRepository = habitRepository;
        this.userDao = userDao;
    }

    public void saveHabit(Habit habit, String username) {
        User user = userDao.getUserByUsername(username);

        habit.setUser(user);

        habitRepository.save(habit);
    }

    public void deleteHabit(String habitId) {
        habitRepository.deleteById(habitId);
    }

    public Habit updateHabit(UpdateHabitRequest updateHabitRequest, String habitId) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new HabitNotFoundException(habitId));

        habit.setName(updateHabitRequest.getName());
        habit.setDescription(updateHabitRequest.getDescription());
        habit.setCategory(updateHabitRequest.getCategory().toString());
        habit.setFrequency(updateHabitRequest.getFrequency().toString());
        habit.setStartDate(updateHabitRequest.getStartDate());

        return habitRepository.save(habit);
    }

    public List<Habit> getUserHabits(String username) {
        User user = userDao.getUserByUsername(username);
        return habitRepository.findAllByUserId(user.getId());
    }
}
