package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.HabitFrequency;
import com.grits.habittracker.repository.habit.HabitFrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HabitFrequencyDao {

    private final HabitFrequencyRepository habitFrequencyRepository;

    @Autowired
    public HabitFrequencyDao(HabitFrequencyRepository habitFrequencyRepository) {
        this.habitFrequencyRepository = habitFrequencyRepository;
    }

    public List<HabitFrequency> getAllFrequencies() {
        return habitFrequencyRepository.findAll();
    }
}
