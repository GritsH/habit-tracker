package com.grits.habittracker.mapper;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.model.request.CreateHabitRequest;
import com.grits.habittracker.model.request.UpdateHabitRequest;
import com.grits.habittracker.model.response.HabitResponse;
import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.model.type.FrequencyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HabitMapperTest {

    private final HabitMapper habitMapper = Mappers.getMapper(HabitMapper.class);
    private Habit habit;

    @BeforeEach
    void setUp() {
        habit = new Habit();
        habit.setId("id123");
        habit.setName("Exercise");
        habit.setDescription("Daily exercise");
        habit.setCategory(CategoryType.OTHER);
        habit.setCreatedAt(LocalDate.now());
        habit.setStartDate(LocalDate.of(2026, 2, 1));
        habit.setUser(new User());
    }

    @Test
    @DisplayName("should map created dto to entity")
    void createDtoToEntity() {
        CreateHabitRequest createHabitRequest = new CreateHabitRequest(
                "name",
                "description",
                LocalDate.of(2026, 2, 1),
                FrequencyType.DAILY,
                CategoryType.ARTS_AND_CRAFTS
        );

        Habit habit = habitMapper.toHabit(createHabitRequest);

        assertThat(habit).isNotNull();
        assertThat(habit)
                .usingRecursiveComparison()
                .ignoringFields("id", "user", "createdAt", "version")
                .isEqualTo(createHabitRequest);

        assertThat(habit.getCreatedAt()).isEqualTo(LocalDate.now());

        assertThat(habit.getId()).isNull();
        assertThat(habit.getUser()).isNull();

    }

    @Test
    @DisplayName("should return null when CreateHabitRequest is null")
    void nullCreateDtoToEntity() {
        assertThat(habitMapper.toHabit(null)).isNull();
    }

    @Test
    @DisplayName("should map entity to a response dto")
    void entityToDto() {

        HabitResponse response = habitMapper.toDto(habit);

        assertThat(response).isNotNull();
        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("frequency", "category")
                .isEqualTo(habit);

        assertThat(response.getCategory()).isEqualTo(CategoryType.OTHER);
    }

    @Test
    @DisplayName("should return null when entity is null")
    void nullEntityToDto() {
        assertThat(habitMapper.toDto(null)).isNull();
    }

    @Test
    @DisplayName("should map a list of entities to a list of response dto")
    void entityListToDtoList() {
        List<Habit> habits = List.of(habit);

        List<HabitResponse> responses = habitMapper.toDtoList(habits);

        assertThat(responses).isNotNull().hasSameSizeAs(habits);
        assertThat(responses.get(0))
                .usingRecursiveComparison()
                .ignoringFields("user", "frequency", "category")
                .isEqualTo(habit);
        assertThat(responses.get(0).getCategory()).isEqualTo(CategoryType.OTHER);
    }

    @Test
    @DisplayName("should update all non-null fields from request")
    void updateHabit() {
        UpdateHabitRequest request = new UpdateHabitRequest(
                "Updated Exercise",
                "Updated description",
                LocalDate.of(2026, 1, 15),
                FrequencyType.WEEKLY,
                CategoryType.PHYSICAL_HEALTH
        );


        Habit habit = new Habit();
        habit.setId("id123");
        habit.setVersion(0L);
        habit.setName("Old Exercise");
        habit.setDescription("Old description");
        habit.setCategory(CategoryType.MENTAL_HEALTH);
        habit.setStartDate(LocalDate.of(2025, 12, 1));
        habit.setCreatedAt(LocalDate.now());
        habit.setUser(new User());

        habitMapper.updateHabit(request, habit);

        assertThat(habit.getId()).isEqualTo("id123");
        assertThat(habit.getUser()).isNotNull();
        assertThat(habit.getCreatedAt()).isEqualTo(LocalDate.now());

        assertThat(habit)
                .usingRecursiveComparison()
                .ignoringFields("id", "user", "createdAt", "version")
                .isEqualTo(request);
    }

    @Test
    @DisplayName("should ignore null fields from request")
    void updateHabitWithNullFields() {
        UpdateHabitRequest request = new UpdateHabitRequest(
                "Updated Exercise",
                null,
                null,
                null,
                null
        );


        Habit habit = new Habit();
        habit.setId("id123");
        habit.setVersion(0L);
        habit.setName("Old Exercise");
        habit.setDescription("Old description");
        habit.setCategory(CategoryType.MENTAL_HEALTH);
        habit.setStartDate(LocalDate.of(2025, 12, 1));
        habit.setCreatedAt(LocalDate.now());
        habit.setUser(new User());

        habitMapper.updateHabit(request, habit);

        assertThat(habit.getName()).isEqualTo(request.getName());
        assertThat(habit).hasNoNullFieldsOrProperties();
    }
}