package com.grits.habittracker.service.habit;

import com.grits.habittracker.dao.habit.HabitDao;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.exception.HabitNotFoundException;
import com.grits.habittracker.exception.UserNotFoundException;
import com.grits.habittracker.mapper.HabitMapper;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.model.response.HabitResponse;
import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HabitServiceTest {

    @Mock
    private HabitDao habitDao;

    @Mock
    private HabitMapper habitMapper;

    @Mock
    private UpdateHabitRequest updateHabitRequest;

    @Mock
    private CreateHabitRequest createHabitRequest;

    @Mock
    private HabitResponse habitResponse;

    @Mock
    private Habit habit;

    @InjectMocks
    private HabitService habitService;

    @AfterEach
    public void after() {
        verifyNoMoreInteractions(habitDao, habitMapper);
    }

    @Test
    @DisplayName("should create a new habit")
    void createNewHabit() {
        when(habitMapper.createDtoToEntity(createHabitRequest)).thenReturn(habit);

        habitService.createNewHabit("username", createHabitRequest);

        verify(habitDao).saveHabit(habit, "username");
    }

    @Test
    @DisplayName("should return the list of all habits")
    void getAllHabits() {
        List<Habit> habits = List.of(habit);
        List<HabitResponse> habitResponses = List.of(habitResponse);

        when(habitDao.getUserHabits("username")).thenReturn(habits);
        when(habitMapper.entityListToDtoList(habits)).thenReturn(habitResponses);

        List<HabitResponse> userHabits = habitService.getAllHabits("username");

        assertThat(userHabits).isNotNull();
        assertThat(userHabits).isEqualTo(habitResponses);
    }

    @Test
    @DisplayName("should delete a habit")
    void deleteHabit() {
        doNothing().when(habitDao).deleteHabit("id123");

        habitService.deleteHabit("id123");

        verify(habitDao).deleteHabit("id123");
    }

    @Test
    @DisplayName("should update the habit")
    void updateHabit() {
        Habit updatedHabit = new Habit();
        updatedHabit.setId("id123");
        updatedHabit.setName("Updated Exercise");
        updatedHabit.setCategory("MENTAL_HEALTH");
        updatedHabit.setFrequency("WEEKLY");

        HabitResponse updatedResponse = new HabitResponse(
                "id123",
                "Updated Exercise",
                LocalDate.now(),
                LocalDate.of(2026, 10, 1),
                "Updated description",
                FrequencyType.WEEKLY,
                CategoryType.MENTAL_HEALTH
        );

        when(habitDao.updateHabit(updateHabitRequest, "id123")).thenReturn(updatedHabit);
        when(habitMapper.entityToDto(updatedHabit)).thenReturn(updatedResponse);

        HabitResponse result = habitService.updateHabit("id123", updateHabitRequest);

        assertThat(result).isNotNull();
        assertThat(result).usingRecursiveComparison().isEqualTo(updatedResponse);
    }

    @Test
    @DisplayName("should throw exception when updating the habit")
    void updateHabitWithException() {
        when(habitDao.updateHabit(updateHabitRequest, "id123")).thenThrow(HabitNotFoundException.class);

        assertThatThrownBy(() -> habitService.updateHabit("id123", updateHabitRequest)).isInstanceOf(HabitNotFoundException.class);
    }
}