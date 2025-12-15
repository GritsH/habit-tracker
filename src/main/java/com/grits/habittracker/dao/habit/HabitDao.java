package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.repository.habit.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitDao {

    private final HabitRepository habitRepository;

    @Autowired
    public HabitDao(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public void saveHabit(Habit habit) {
        habitRepository.save(habit);
    }

    public void deleteHabit(String habitId) {
        habitRepository.deleteById(habitId);
    }

    public Habit updateHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public List<Habit> getUserHabits(String userId) {
        return habitRepository.findAllByUserId(userId);
    }
}
