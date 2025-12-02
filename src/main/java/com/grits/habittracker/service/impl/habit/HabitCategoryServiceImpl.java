package com.grits.habittracker.service.impl.habit;

import com.grits.habittracker.entity.habit.HabitCategory;
import com.grits.habittracker.repository.habit.HabitCategoryRepository;
import com.grits.habittracker.service.habit.HabitCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitCategoryServiceImpl implements HabitCategoryService {

    private final HabitCategoryRepository repository;

    @Autowired
    public HabitCategoryServiceImpl(HabitCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HabitCategory> getAll() {
        return repository.findAll();
    }

    @Override
    public List<HabitCategory> findAllByCategoryNameContaining(String categoryName) {
        return repository.findAllByCategoryNameContainingIgnoreCase(categoryName);
    }
}
