package com.grits.habittracker.service.habit;

import com.grits.habittracker.entity.habit.HabitCategory;

import java.util.List;

public interface HabitCategoryService {

    List<HabitCategory> getAll();

    List<HabitCategory> findAllByCategoryNameContaining(String categoryName);
}
