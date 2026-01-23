package com.grits.server.dao.habit;

import com.grits.server.entity.User;
import com.grits.server.entity.habit.Habit;
import com.grits.server.exception.HabitNotFoundException;
import com.grits.server.exception.HabitUpdateFailedException;
import com.grits.server.exception.UserNotFoundException;
import com.grits.server.repository.StreakRepository;
import com.grits.server.repository.UserRepository;
import com.grits.server.repository.habit.HabitCompletionRepository;
import com.grits.server.repository.habit.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HabitDao {

    private final HabitRepository habitRepository;

    private final StreakRepository streakRepository;

    private final HabitCompletionRepository habitCompletionRepository;

    private final UserRepository userRepository;

    @CacheEvict(value = "userHabits", key = "#userId")
    public Habit saveHabit(Habit habit, String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        User user = userRepository.getReferenceById(userId);
        habit.setUser(user);
        return habitRepository.save(habit);
    }

    @Caching(evict = {
            @CacheEvict(value = "habitCompletions", key = "#habitId"),
            @CacheEvict(value = "streak", key = "#habitId"),
            @CacheEvict(value = "userHabits", key = "#userId"),
            @CacheEvict(value = "habit", key = "#habitId")
    })
    public void deleteHabit(String habitId, String userId) {
        checkHabitOwnership(habitId, userId);
        habitCompletionRepository.deleteAllByHabitId(habitId);
        streakRepository.deleteAllByHabitId(habitId);
        habitRepository.deleteById(habitId);
    }

    @Cacheable(value = "habit", key = "#id")
    public Habit getHabitById(String id) {
        return habitRepository.findById(id).orElseThrow(() -> new HabitNotFoundException(id));
    }

    @CachePut(value = "habit", key = "#updated.id")
    @CacheEvict(value = "userHabits", key = "#updated.user.id")
    public Habit updateHabit(Habit updated) {
        try {
            return habitRepository.save(updated);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new HabitUpdateFailedException(updated.getId());
        }
    }

    @Cacheable(value = "userHabits", key = "#userId")
    public List<Habit> getUserHabits(String userId) {
        return habitRepository.findAllByUserId(userId);
    }

    private void checkHabitOwnership(String habitId, String userId) {
        if (!habitRepository.existsByIdAndUserId(habitId, userId)) {
            throw new HabitNotFoundException();
        }
    }
}
