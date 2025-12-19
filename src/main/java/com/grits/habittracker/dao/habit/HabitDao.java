package com.grits.habittracker.dao.habit;

import com.grits.habittracker.dao.UserDao;
import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.repository.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HabitDao {

    private final HabitRepository habitRepository;

    private final UserDao userDao;

    public Habit saveHabit(Habit habit, String userId) {
        User user = userDao.getUserReferenceById(userId);

        habit.setUser(user);

        return habitRepository.save(habit);
    }

    public void deleteHabit(String habitId) {
        habitRepository.deleteById(habitId);
    }

    public Habit getHabitById(String id) {
        return habitRepository.findById(id).orElseThrow(() -> new HabitNotFoundException(id));
    }

    public Habit saveUpdatedHabit(Habit updated) {
        return habitRepository.save(updated);
    }

    public List<Habit> getUserHabits(String userId) {
        return habitRepository.findAllByUserId(userId);
    }
}
