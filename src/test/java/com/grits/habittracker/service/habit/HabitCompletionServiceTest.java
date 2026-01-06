package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.StreakDao;
import com.grits.habittracker.dao.habit.HabitCompletionDao;
import com.grits.habittracker.entity.habit.HabitCompletion;
import com.grits.habittracker.mapper.HabitCompletionMapper;
import com.grits.habittracker.model.response.HabitCompletionResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitCompletionServiceTest {

    @Mock
    private HabitCompletionDao habitCompletionDao;

    @Mock
    private StreakDao streakDao;

    @Mock
    private HabitCompletionMapper habitCompletionMapper;

    @InjectMocks
    private HabitCompletionService habitCompletionService;

    private HabitCompletionResponse habitCompletionResponse;

    private HabitCompletion habitCompletion;

    @BeforeEach
    void setUp() {
        habitCompletion = new HabitCompletion();
        habitCompletion.setId("id");
        habitCompletion.setLoggedAt(LocalDate.now());
        habitCompletion.setCompletionLog("completion_log");

        habitCompletionResponse = new HabitCompletionResponse("id", LocalDate.now(), "completion_log");
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                habitCompletionDao,
                streakDao,
                habitCompletionMapper
        );
    }

    @Test
    @DisplayName("should log habit completion")
    void logCompletion() {
        when(habitCompletionMapper.toEntity("habit_id")).thenReturn(habitCompletion);
        when(habitCompletionDao.saveCompletion("habit_id", "user_id", habitCompletion)).thenReturn(habitCompletion);
        when(habitCompletionMapper.toResponse(habitCompletion)).thenReturn(habitCompletionResponse);

        HabitCompletionResponse response = habitCompletionService.logCompletion("habit_id", "user_id");

        verify(habitCompletionDao).saveCompletion("habit_id", "user_id", habitCompletion);
        verify(streakDao).incrementStreak("habit_id");

        assertThat(response).isSameAs(habitCompletionResponse);
    }

    @Test
    @DisplayName("should get log history")
    void getHabitLogHistory() {
        when(habitCompletionDao.getHabitLogHistory("habit_id", "user_id")).thenReturn(List.of(habitCompletion));
        when(habitCompletionMapper.toDtoList(List.of(habitCompletion))).thenReturn(List.of(habitCompletionResponse));

        List<HabitCompletionResponse> responses = habitCompletionService.getHabitLogHistory("habit_id", "user_id");

        assertThat(responses).isNotNull().hasSize(1);
    }
}