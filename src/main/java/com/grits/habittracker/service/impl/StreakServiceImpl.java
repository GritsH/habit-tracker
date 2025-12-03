package com.grits.habittracker.service.impl;

import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.repository.StreakRepository;
import com.grits.habittracker.service.StreakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StreakServiceImpl implements StreakService {

    private final StreakRepository repository;

    @Autowired
    public StreakServiceImpl(StreakRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Streak streak) {
        repository.save(streak);
    }

    @Override
    public List<Streak> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Streak> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Streak> findByHabitId(Long id) {
        return repository.findByHabitId(id);
    }
}
