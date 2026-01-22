package com.grits.server.mapper;

import com.grits.server.entity.Streak;
import com.grits.api.model.response.StreakResponse;
import com.grits.api.model.type.FrequencyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class StreakMapperTest {

    private final StreakMapper streakMapper = Mappers.getMapper(StreakMapper.class);

    @Test
    @DisplayName("should map entity to dto")
    void toDto() {
        Streak streak = new Streak();
        streak.setId("id");
        streak.setHabitId("habit_id");
        streak.setResetAt(LocalDate.now().plusDays(1));
        streak.setFrequency(FrequencyType.DAILY);
        streak.setCurrentStreak(10);
        streak.setLongestStreak(100);

        StreakResponse response = streakMapper.toDto(streak);

        assertThat(response)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(streak);
    }

    @Test
    @DisplayName("should return null if entity is null")
    void toNullDto() {
        assertThat(streakMapper.toDto(null)).isNull();
    }
}