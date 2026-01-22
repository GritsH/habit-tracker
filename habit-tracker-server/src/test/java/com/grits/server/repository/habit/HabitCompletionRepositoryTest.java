package com.grits.server.repository.habit;

import com.grits.server.entity.habit.HabitCompletion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class HabitCompletionRepositoryTest {

    @Autowired
    private HabitCompletionRepository habitCompletionRepository;

    private HabitCompletion testHabitCompletion;

    @BeforeEach
    public void setUp() {
        testHabitCompletion = new HabitCompletion();
        testHabitCompletion.setHabitId("habit_id");
        testHabitCompletion.setLoggedAt(LocalDate.now());
        testHabitCompletion.setCompletionLog("habit_id_" + LocalDate.now());

        habitCompletionRepository.save(testHabitCompletion);
    }

    @AfterEach
    public void tearDown() {
        habitCompletionRepository.delete(testHabitCompletion);
    }

    @Test
    @DisplayName("should find all by the habit id")
    void findAllByHabitId() {
        List<HabitCompletion> completions = habitCompletionRepository.findAllByHabitId("habit_id");

        assertThat(completions).contains(testHabitCompletion);
    }

    @Test
    @DisplayName("should not find all by the wrong habit id")
    void findAllWithWrongHabitId() {
        List<HabitCompletion> completions = habitCompletionRepository.findAllByHabitId("wrong_id");

        assertThat(completions).isEmpty();
    }

    @Test
    @DisplayName("should delete by a habit id")
    void deleteAllByHabitId() {
        habitCompletionRepository.deleteAllByHabitId("habit_id");

        assertThat(habitCompletionRepository.findAllByHabitId("habit_id")).isEmpty();
    }
}