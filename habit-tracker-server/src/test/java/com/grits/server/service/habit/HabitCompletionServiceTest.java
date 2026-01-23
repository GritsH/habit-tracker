package com.grits.server.service.habit;

import com.grits.server.dao.StreakDao;
import com.grits.server.dao.habit.HabitCompletionDao;
import com.grits.server.entity.habit.HabitCompletion;
import com.grits.server.mapper.HabitCompletionMapper;
import com.grits.api.model.response.HabitCompletionResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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
        habitCompletion = mock(HabitCompletion.class);
        habitCompletionResponse = mock(HabitCompletionResponse.class);
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
        assertThat(responses).contains(habitCompletionResponse);
    }
}