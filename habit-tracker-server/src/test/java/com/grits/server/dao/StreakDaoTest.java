package com.grits.server.dao;

import com.grits.server.entity.Streak;
import com.grits.server.exception.HabitNotFoundException;
import com.grits.server.exception.StreakNotFoundException;
import com.grits.api.model.type.FrequencyType;
import com.grits.server.repository.StreakRepository;
import com.grits.server.repository.habit.HabitRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StreakDaoTest {

    @Mock
    private StreakRepository streakRepository;

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private StreakDao streakDao;

    private Streak streak;

    @BeforeEach
    void setUp() {
        streak = new Streak();
        streak.setFrequency(FrequencyType.DAILY);
        streak.setCurrentStreak(10);
        streak.setLongestStreak(10);
        streak.setHabitId("habit_id");
        streak.setResetAt(LocalDate.now().minusDays(1));
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(
                streakRepository,
                habitRepository
        );
    }

    @Test
    @DisplayName("should save new streak")
    void save() {
        Streak streakToSave = new Streak();
        streakToSave.setFrequency(FrequencyType.DAILY);
        streakToSave.setHabitId("habit_id");

        streakDao.save("habit_id", FrequencyType.DAILY);

        verify(streakRepository).save(streakToSave);
    }

    @Test
    @DisplayName("should update a streak")
    void updateStreak() {
        when(streakRepository.findByHabitId("habit_id")).thenReturn(Optional.of(streak));

        streakDao.updateStreak("habit_id", FrequencyType.WEEKLY);

        assertThat(streak.getFrequency()).isEqualTo(FrequencyType.WEEKLY);

        verify(streakRepository).save(streak);
    }

    @Test
    @DisplayName("should increment streak")
    void incrementStreak() {
        streak.setResetAt(LocalDate.now());
        when(streakRepository.findByHabitIdWithLock("habit_id")).thenReturn(Optional.of(streak));

        streakDao.incrementStreak("habit_id");

        assertThat(streak.getCurrentStreak()).isEqualTo(11);
        assertThat(streak.getLongestStreak()).isEqualTo(11);
        assertThat(streak.getResetAt()).isEqualTo(LocalDate.now().plusDays(1));

        verify(streakRepository).save(streak);
    }

    @Test
    @DisplayName("should set current streak to 1 when incrementing it (missed date)")
    void incrementStreakSet1() {
        when(streakRepository.findByHabitIdWithLock("habit_id")).thenReturn(Optional.of(streak));

        streakDao.incrementStreak("habit_id");

        assertThat(streak.getCurrentStreak()).isEqualTo(1);
        assertThat(streak.getResetAt()).isEqualTo(LocalDate.now().plusDays(1));

        verify(streakRepository).save(streak);
    }

    @Test
    @DisplayName("should throw exception when incrementing streak")
    void incrementStreakWithException() {
        when(streakRepository.findByHabitIdWithLock("habit_id")).thenThrow(StreakNotFoundException.class);

        assertThatThrownBy(() -> streakDao.incrementStreak("habit_id"))
                .isInstanceOf(StreakNotFoundException.class);
    }

    @Test
    @DisplayName("should call repository multiple times while resetting missed streaks")
    void resetAllMissedStreaks() {
        when(streakRepository.resetMissedStreaks(1000))
                .thenReturn(1000)
                .thenReturn(500)
                .thenReturn(0);

        streakDao.resetAllMissedStreaks();

        verify(streakRepository, times(3)).resetMissedStreaks(1000);
    }

    @Test
    @DisplayName("should get a streak")
    void getStreak() {
        when(habitRepository.existsByIdAndUserId("habit_id", "user_id")).thenReturn(true);
        when(streakRepository.findByHabitId("habit_id")).thenReturn(Optional.of(streak));

        Streak returnedStreak = streakDao.getStreak("habit_id", "user_id");

        assertThat(returnedStreak).isSameAs(streak);
    }

    @Test
    @DisplayName("should throw exception if habit or user id is wrong")
    void getStreakWithBadIds() {
        when(habitRepository.existsByIdAndUserId("habit_id", "user_id")).thenReturn(false);

        assertThatThrownBy(() -> streakDao.getStreak("habit_id", "user_id"))
                .isInstanceOf(HabitNotFoundException.class);
    }

    @Test
    @DisplayName("should throw exception if streak not found")
    void getNoStreak() {
        when(habitRepository.existsByIdAndUserId("habit_id", "user_id")).thenReturn(true);
        when(streakRepository.findByHabitId("habit_id")).thenThrow(StreakNotFoundException.class);

        assertThatThrownBy(() -> streakDao.getStreak("habit_id", "user_id"))
                .isInstanceOf(StreakNotFoundException.class);
    }
}