package com.grits.habittracker.dao.habit;

import com.grits.habittracker.dao.UserDao;
import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitDaoTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private HabitDao habitDao;

    private User user;
    private Habit habit;
    private UpdateHabitRequest updateHabitRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("id001");
        user.setUsername("username");

        habit = new Habit();
        habit.setId("id123");
        habit.setName("Exercise");
        habit.setDescription("Daily exercise");
        habit.setCategory("OTHER");
        habit.setFrequency("DAILY");
        habit.setStartDate(LocalDate.now());
        habit.setUser(user);

        updateHabitRequest = new UpdateHabitRequest(
                "Updated",
                "Updated description",
                LocalDate.of(2026, 2, 1),
                FrequencyType.MONTHLY,
                CategoryType.ENVIRONMENTAL
        );
    }

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(habitRepository, userDao);
    }

    @Test
    @DisplayName("should save a new habit")
    void saveHabit() {
        when(userDao.getUserByUsername("username")).thenReturn(user);

        habitDao.saveHabit(habit, "username");

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
        when(habitRepository.findById("id123")).thenReturn(Optional.of(habit));
        when(habitRepository.save(habit)).thenReturn(habit);

        Habit updatedHabit = habitDao.updateHabit(updateHabitRequest, "id123");

        verify(habitRepository).save(habit);

        assertThat(updatedHabit)
                .usingRecursiveComparison()
                .ignoringFields("id", "user", "createdAt", "frequency", "category")
                .isEqualTo(updateHabitRequest);
        assertThat(updatedHabit.getCategory()).isEqualTo(updateHabitRequest.getCategory().toString());
        assertThat(updatedHabit.getFrequency()).isEqualTo(updateHabitRequest.getFrequency().toString());

    }

    @Test
    @DisplayName("should throw a HabitNotFound exception")
    void updateWithException() {
        when(habitRepository.findById("id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> habitDao.updateHabit(updateHabitRequest, "id"))
                .isInstanceOf(HabitNotFoundException.class)
                .hasMessage("Habit with id id not found");
    }

    @Test
    @DisplayName("should return all user's habits")
    void getUserHabits() {
        List<Habit> expectedHabits = List.of(habit);

        when(userDao.getUserByUsername("username")).thenReturn(user);
        when(habitRepository.findAllByUserId(user.getId())).thenReturn(expectedHabits);

        List<Habit> actualHabits = habitDao.getUserHabits("username");

        assertThat(actualHabits).isNotEmpty();
        assertThat(actualHabits).isEqualTo(expectedHabits);
    }
}