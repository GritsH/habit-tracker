package com.grits.habittracker.service;

import com.grits.habittracker.dao.StreakDao;
import com.grits.habittracker.entity.Streak;
import com.grits.habittracker.mapper.StreakMapper;
import com.grits.habittracker.model.response.StreakResponse;
import com.grits.habittracker.model.type.FrequencyType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StreakServiceTest {

    @Mock
    private StreakDao streakDao;

    @Mock
    private StreakMapper streakMapper;

    @InjectMocks
    private StreakService streakService;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                streakDao,
                streakMapper
        );
    }

    @Test
    @DisplayName("should get a streak for a habit")
    void getStreak() {
        Streak streak = new Streak();
        streak.setLongestStreak(10);
        streak.setCurrentStreak(10);
        streak.setFrequency(FrequencyType.DAILY);
        streak.setHabitId("habit_id");
        StreakResponse streakResponse = new StreakResponse(
                10,
                10,
                "habit_id",
                LocalDate.now(),
                FrequencyType.DAILY
        );
        when(streakDao.getStreak("habit_id", "user_id")).thenReturn(streak);
        when(streakMapper.toDto(streak)).thenReturn(streakResponse);

        StreakResponse response = streakService.getStreak("habit_id", "user_id");

        assertThat(response).isSameAs(streakResponse);
    }
}