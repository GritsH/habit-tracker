package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.exception.HabitUpdateFailedException;
import com.grits.habittracker.repository.UserRepository;
import com.grits.habittracker.repository.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HabitDao {

    private final HabitRepository habitRepository;

    private final UserRepository userRepository;

    public Habit saveHabit(Habit habit, String userId) {
        User user = userRepository.getReferenceById(userId);

        habit.setUser(user);

        return habitRepository.save(habit);
    }

    public void deleteHabit(String habitId) {
        habitRepository.deleteById(habitId);
    }

    public Habit getHabitById(String id) {
        return habitRepository.findById(id).orElseThrow(() -> new HabitNotFoundException(id));
    }

    public Habit updateHabit(Habit updated) {
        try {
            return habitRepository.save(updated);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new HabitUpdateFailedException(updated.getId());
        }
    }

    public List<Habit> getUserHabits(String userId) {
        return habitRepository.findAllByUserId(userId);
    }
}
