package com.grits.habittracker.dao.habit;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
import com.grits.habittracker.repository.UserRepository;
import com.grits.habittracker.repository.habit.HabitRepository;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitDaoTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HabitDao habitDao;

    private User user;
    private Habit habit;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("id001");
        user.setUsername("username");

        habit = new Habit();
        habit.setId("id123");
        habit.setName("Exercise");
        habit.setDescription("Daily exercise");
        habit.setCategory(CategoryType.OTHER);
        habit.setFrequency(FrequencyType.DAILY);
        habit.setStartDate(LocalDate.now());
        habit.setUser(user);
    }

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(
                habitRepository,
                userRepository
        );
    }

    @Test
    @DisplayName("should save a new habit")
    void saveHabit() {
        when(userRepository.getReferenceById("id")).thenReturn(user);

        habitDao.saveHabit(habit, "id");

        verify(habitRepository).save(habit);
    }

    @Test
    @DisplayName("should delete a habit")
    void deleteHabit() {
        doNothing().when(habitRepository).deleteById("id123");

        habitDao.deleteHabit("id123");

        verify(habitRepository).deleteById("id123");
    }

    @Test
    @DisplayName("should update existing habit")
    void updateHabit() {
        habitDao.updateHabit(habit);

        verify(habitRepository).save(habit);
    }

    @Test
    @DisplayName("should return all user's habits")
    void getUserHabits() {
        List<Habit> expectedHabits = List.of(habit);

        when(habitRepository.findAllByUserId("userId")).thenReturn(expectedHabits);

        List<Habit> actualHabits = habitDao.getUserHabits("userId");

        assertThat(actualHabits).isNotEmpty();
        assertThat(actualHabits).isEqualTo(expectedHabits);
    }
}