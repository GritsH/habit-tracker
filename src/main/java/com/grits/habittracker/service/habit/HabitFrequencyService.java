package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.habit.HabitFrequencyDao;
import com.grits.habittracker.entity.habit.HabitFrequency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HabitFrequencyService {

    private final HabitFrequencyDao habitFrequencyDao;

    public List<HabitFrequency> getAllFrequencies() {
        return habitFrequencyDao.getAllFrequencies();
    }

    public HabitFrequency getByName(String name) {
        return habitFrequencyDao.getByName(name);
    }
}
