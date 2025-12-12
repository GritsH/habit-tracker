package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.habit.HabitFrequencyDao;
import com.grits.habittracker.entity.habit.HabitFrequency;
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
class HabitFrequencyServiceTest {

    @Mock
    private HabitFrequencyDao habitFrequencyDao;

    @InjectMocks
    private HabitFrequencyService habitFrequencyService;

    @Test
    @DisplayName("should retrieve all frequencies")
    void getAllFrequencies() {
        HabitFrequency habitFrequency = new HabitFrequency("id", "frequency_name");

        when(habitFrequencyDao.getAllFrequencies()).thenReturn(List.of(habitFrequency));

        List<HabitFrequency> result = habitFrequencyService.getAllFrequencies();

        assertThat(result).isNotEmpty();
        assertThat(result).contains(habitFrequency);

        verifyNoMoreInteractions(habitFrequencyDao);
    }
}