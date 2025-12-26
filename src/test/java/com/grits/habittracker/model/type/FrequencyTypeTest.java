package com.grits.habittracker.model.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FrequencyTypeTest {

    @Test
    @DisplayName("Should update resetAt date and return tomorrow")
    void updateResetAtDaily() {
        LocalDate result = FrequencyType.DAILY.updateResetAt();

        assertThat(result).isEqualTo(LocalDate.now().plusDays(1));
    }

    @Test
    @DisplayName("Should update resetAt date and return two days after tomorrow")
    void updateResetAtEveryTwoDays() {
        LocalDate result = FrequencyType.EVERY_TWO_DAYS.updateResetAt();

        assertThat(result).isEqualTo(LocalDate.now().plusDays(2));
    }

    @Test
    @DisplayName("Should update resetAt date and return the same day next week")
    void updateResetAtWeekly() {
        LocalDate result = FrequencyType.WEEKLY.updateResetAt();
        LocalDate today = LocalDate.now();

        assertThat(result).isEqualTo(today.plusDays(7));
        assertThat(result.getDayOfWeek()).isEqualTo(today.getDayOfWeek());
    }

    @Test
    @DisplayName("Should update resetAt date and return the same day next fortnight")
    void updateResetAtBiweekly() {
        LocalDate result = FrequencyType.BIWEEKLY.updateResetAt();
        LocalDate today = LocalDate.now();

        assertThat(result).isEqualTo(today.plusWeeks(2));
        assertThat(result.getDayOfWeek()).isEqualTo(today.getDayOfWeek());
    }

    @Test
    @DisplayName("Should update resetAt date and return the same day next month")
    void updateResetAtMonthly() {
        LocalDate result = FrequencyType.MONTHLY.updateResetAt();
        LocalDate today = LocalDate.now();

        assertThat(result.getYear()).isGreaterThanOrEqualTo(today.getYear());
        assertThat(result.getDayOfMonth()).isEqualTo(today.getDayOfMonth());
    }
}