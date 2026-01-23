package com.grits.server.mapper;

import com.grits.server.entity.habit.HabitCompletion;
import com.grits.api.model.response.HabitCompletionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HabitCompletionMapperTest {

    private final HabitCompletionMapper habitCompletionMapper = Mappers.getMapper(HabitCompletionMapper.class);

    private HabitCompletion habitCompletion;

    @BeforeEach
    void setUp() {
        habitCompletion = new HabitCompletion();
        habitCompletion.setHabitId("habit_id");
        habitCompletion.setCompletionLog("habit_id_" + LocalDate.now());
        habitCompletion.setLoggedAt(LocalDate.now());
    }

    @Test
    @DisplayName("should map dto to entity")
    void toEntity() {
        HabitCompletion result = habitCompletionMapper.toEntity("habit_id");

        assertThat(result.getHabitId()).isEqualTo("habit_id");
        assertThat(result.getLoggedAt()).isEqualTo(LocalDate.now());
        assertThat(result.getCompletionLog()).isNull();
    }

    @Test
    @DisplayName("should return null if dto is null")
    void toNullEntity() {
        assertThat(habitCompletionMapper.toEntity(null)).isNull();
    }

    @Test
    @DisplayName("should map entity to dto")
    void toResponse() {
        HabitCompletionResponse response = habitCompletionMapper.toResponse(habitCompletion);

        assertThat(response.getLoggedAt()).isEqualTo(habitCompletion.getLoggedAt());
        assertThat(response.getCompletionLog()).isEqualTo(habitCompletion.getCompletionLog());
    }

    @Test
    @DisplayName("should return null if entity is null")
    void toNullResponse() {
        assertThat(habitCompletionMapper.toResponse(null)).isNull();
    }

    @Test
    @DisplayName("should map list of entities to the dto list")
    void toDtoList() {
        List<HabitCompletion> completions = List.of(habitCompletion);

        List<HabitCompletionResponse> responses = habitCompletionMapper.toDtoList(completions);

        assertThat(responses).isNotNull().hasSameSizeAs(completions);
        assertThat(responses.get(0).getCompletionLog()).isEqualTo(habitCompletion.getCompletionLog());
    }
}