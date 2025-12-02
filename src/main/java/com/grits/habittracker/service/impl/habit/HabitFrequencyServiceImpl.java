package com.grits.habittracker.service.impl.habit;

import com.grits.habittracker.entity.habit.HabitFrequency;
import com.grits.habittracker.repository.habit.HabitFrequencyRepository;
import com.grits.habittracker.service.habit.HabitFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitFrequencyServiceImpl implements HabitFrequencyService {

    private final HabitFrequencyRepository repository;

    @Autowired
    public HabitFrequencyServiceImpl(HabitFrequencyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HabitFrequency> getAll() {
        return repository.findAll();
    }

    @Override
    public List<HabitFrequency> getByNameContaining(String name) {
        return repository.getByNameContainingIgnoreCase(name);
    }
}
