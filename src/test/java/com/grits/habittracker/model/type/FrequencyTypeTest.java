package com.grits.habittracker.model.type;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FrequencyTypeTest {

    @Test
    @DisplayName("Should update resetAt date and return tomorrow")
    void updateResetAtDaily() {
        LocalDate today = LocalDate.now();
        LocalDate result = FrequencyType.DAILY.calculateResetAt();

        assertThat(result).isEqualTo(today.plusDays(1));
    }

    @Test
    @DisplayName("Should update resetAt date and return day after tomorrow")
    void updateResetAtEveryTwoDays() {
        LocalDate today = LocalDate.now();
        LocalDate result = FrequencyType.EVERY_TWO_DAYS.calculateResetAt();

        assertThat(result).isEqualTo(today.plusDays(2));
    }

    @Test
    @DisplayName("Should update resetAt date and return the same day next week")
    void updateResetAtWeekly() {
        LocalDate today = LocalDate.now();
        LocalDate result = FrequencyType.WEEKLY.calculateResetAt();

        assertThat(result).isEqualTo(today.plusDays(7));
    }

    @Test
    @DisplayName("Should update resetAt date and return the same day next fortnight")
    void updateResetAtBiweekly() {
        LocalDate today = LocalDate.now();
        LocalDate result = FrequencyType.BIWEEKLY.calculateResetAt();

        assertThat(result).isEqualTo(today.plusWeeks(2));
    }

    @Test
    @DisplayName("Should update resetAt date and return the same day next month")
    void updateResetAtMonthly() {
        LocalDate today = LocalDate.now();
        LocalDate result = FrequencyType.MONTHLY.calculateResetAt();

        assertThat(result.getYear()).isGreaterThanOrEqualTo(today.getYear());
        assertThat(result.getDayOfMonth()).isEqualTo(today.getDayOfMonth());
    }
}