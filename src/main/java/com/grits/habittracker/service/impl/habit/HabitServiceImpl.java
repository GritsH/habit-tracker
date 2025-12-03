package com.grits.habittracker.service.impl.habit;

import com.grits.habittracker.entity.habit.Habit;
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
        return repository.findAllByUserId(userId);
    }

    @Override
    public List<Habit> findAllByHabitCategoryName(String categoryName) {
        return repository.findAllByHabitCategoryName(categoryName.toUpperCase());
    }

    @Override
    public List<Habit> findAllByUserIdAndCreatedAt(Long userId, Date createdAt) {
        return repository.findAllByUserIdAndCreatedAt(userId, createdAt);
    }

    @Override
    public List<Habit> findAllByNameContaining(String name) {
        return repository.findAllByNameContaining(name.toUpperCase());
    }

    @Override
    public List<Habit> findAllByFrequencyId(Long frequencyId) {
        return repository.findAllByFrequencyId(frequencyId);
    }
}
