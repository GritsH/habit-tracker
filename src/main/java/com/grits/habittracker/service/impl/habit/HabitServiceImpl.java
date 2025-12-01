package com.grits.habittracker.service.impl.habit;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCategory;
import com.grits.habittracker.entity.habit.HabitFrequency;
import com.grits.habittracker.repository.habit.HabitRepository;
import com.grits.habittracker.service.habit.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HabitServiceImpl implements HabitService {

    private final HabitRepository repository;

    @Autowired
    public HabitServiceImpl(HabitRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Habit habit) {
        repository.save(habit);
    }

    @Override
    public Optional<Habit> findById(Long habitId) {
        return repository.findById(habitId);
    }

    @Override
    public List<Habit> findByCreatedAt(Date createdAt) {
        return repository.findByCreatedAt(createdAt);
    }

    @Override
    public List<Habit> findAllByUserId(Long userId) {
        return repository.findAllByUser_Id(userId);
    }

    @Override
    public List<Habit> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    @Override
    public List<Habit> findAllByHabitCategoryName(String categoryName) {
        return repository.findAllByHabitCategory_CategoryName(categoryName);
    }

    @Override
    public List<Habit> findAllByHabitCategory(HabitCategory habitCategory) {
        return repository.findAllByHabitCategory(habitCategory);
    }

    @Override
    public List<Habit> findAllByUserIdAndCreatedAt(Long userId, Date createdAt) {
        return repository.findAllByUser_IdAndCreatedAt(userId, createdAt);
    }

    @Override
    public List<Habit> findAllByNameContaining(String name) {
        return repository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Habit> findAllByFrequencyId(Long frequencyId) {
        return repository.findAllByFrequency_Id(frequencyId);
    }

    @Override
    public List<Habit> findAllByFrequency(HabitFrequency frequency) {
        return repository.findAllByFrequency(frequency);
    }
}
