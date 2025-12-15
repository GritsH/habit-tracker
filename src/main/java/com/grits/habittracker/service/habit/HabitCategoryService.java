package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.habit.HabitCategoryDao;
import com.grits.habittracker.entity.habit.HabitCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitCategoryService {

    private final HabitCategoryDao habitCategoryDao;

    public List<HabitCategory> getAllCategories() {
        return habitCategoryDao.getAllCategories();
    }

    public HabitCategory getByName(String name){
        return habitCategoryDao.getByName(name);
    }
}
