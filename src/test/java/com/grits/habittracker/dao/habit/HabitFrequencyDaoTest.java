package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.HabitFrequency;
import com.grits.habittracker.repository.habit.HabitFrequencyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitFrequencyDaoTest {

    @Mock
    private HabitFrequencyRepository repository;

    @InjectMocks
    private HabitFrequencyDao habitFrequencyDao;

    @Test
    @DisplayName("should get all frequencies")
    void getAllFrequencies() {
        HabitFrequency habitFrequency = new HabitFrequency("id", "frequency_name");

        when(repository.findAll()).thenReturn(List.of(habitFrequency));

        List<HabitFrequency> result = habitFrequencyDao.getAllFrequencies();

        assertThat(result).isNotEmpty();
        assertThat(result).contains(habitFrequency);

        verifyNoMoreInteractions(repository);
    }
}