package com.grits.habittracker.service.impl.habit;

import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.entity.habit.HabitCompletion;
import com.grits.habittracker.repository.habit.HabitCompletionRepository;
import com.grits.habittracker.service.habit.HabitCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HabitCompletionServiceImpl implements HabitCompletionService {

    private final HabitCompletionRepository repository;

    @Autowired
    public HabitCompletionServiceImpl(HabitCompletionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(HabitCompletion habitCompletion) {
        repository.save(habitCompletion);
    }

    @Override
    public List<HabitCompletion> getAll() {
        return repository.getAll();
    }

    @Override
    public List<HabitCompletion> getAllByCompletedAt(Date completedAt) {
        return repository.getAllByCompletedAt(completedAt);
    }

    @Override
    public List<HabitCompletion> findAllByHabitId(Long habitId) {
        return repository.findAllByHabit_Id(habitId);
    }

    @Override
    public List<HabitCompletion> findAllByHabit(Habit habit) {
        return repository.findAllByHabit(habit);
    }
}
