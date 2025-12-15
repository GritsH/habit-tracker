package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.HabitCategory;
import com.grits.habittracker.exception.HabitCategoryNotFoundException;
import com.grits.habittracker.repository.habit.HabitCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitCategoryDao {

    private final HabitCategoryRepository habitCategoryRepository;

    @Autowired
    public HabitCategoryDao(HabitCategoryRepository habitCategoryRepository) {
        this.habitCategoryRepository = habitCategoryRepository;
    }

    public List<HabitCategory> getAllCategories() {
        return habitCategoryRepository.findAll();
    }

    public HabitCategory getByName(String name) {
        return habitCategoryRepository.findByName(name).orElseThrow(
                () -> new HabitCategoryNotFoundException(name)
        );
    }
}
