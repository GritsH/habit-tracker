package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.habit.HabitCompletion;
import com.grits.habittracker.exception.HabitAlreadyCompletedException;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.repository.habit.HabitCompletionRepository;
import com.grits.habittracker.repository.habit.HabitRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitCompletionDaoTest {

    @Mock
    private HabitCompletionRepository completionRepository;

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitCompletionDao habitCompletionDao;

    private HabitCompletion habitCompletion;

    @BeforeEach
    void setUp() {
        habitCompletion = mock(HabitCompletion.class);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                habitRepository,
                completionRepository
        );
    }

    @Test
    @DisplayName("should log habit completion and save in db")
    void saveCompletion() {
        when(habitRepository.existsByIdAndUserId("id", "user_id")).thenReturn(true);

        habitCompletionDao.saveCompletion("id", "user_id", habitCompletion);

        verify(completionRepository).saveAndFlush(habitCompletion);
    }

    @Test
    @DisplayName("should not log habit completion if already logged")
    void saveLoggedCompletion() {
        when(habitRepository.existsByIdAndUserId("id", "user_id")).thenReturn(true);
        when(habitCompletionDao.saveCompletion("id", "user_id", habitCompletion)).thenThrow(DataIntegrityViolationException.class);

        assertThatThrownBy(() -> habitCompletionDao.saveCompletion("id", "user_id", habitCompletion))
                .isInstanceOf(HabitAlreadyCompletedException.class);
    }

    @Test
    @DisplayName("should throw exception if bad habit or user id")
    void saveWithException() {
        when(habitRepository.existsByIdAndUserId("id", "user_id")).thenReturn(false);

        assertThatThrownBy(() -> habitCompletionDao.saveCompletion("id", "user_id", habitCompletion))
                .isInstanceOf(HabitNotFoundException.class);
    }

    @Test
    @DisplayName("should get log history")
    void getHabitLogHistory() {
        when(habitRepository.existsByIdAndUserId("id", "user_id")).thenReturn(true);
        when(completionRepository.findAllByHabitId("id")).thenReturn(List.of(habitCompletion));

        List<HabitCompletion> completions = habitCompletionDao.getHabitLogHistory("id", "user_id");

        assertThat(completions).contains(habitCompletion);
    }

    @Test
    @DisplayName("should throw exception if habit or user id is wrong")
    void getHistoryWithBadIds() {
        when(habitRepository.existsByIdAndUserId("id", "user_id")).thenReturn(false);

        assertThatThrownBy(() -> habitCompletionDao.getHabitLogHistory("id", "user_id"))
                .isInstanceOf(HabitNotFoundException.class);
    }
}