package com.grits.server.repository;

import com.grits.server.entity.Streak;
import com.grits.server.entity.User;
import com.grits.server.entity.habit.Habit;
import com.grits.api.model.type.CategoryType;
import com.grits.api.model.type.FrequencyType;
import com.grits.server.repository.habit.HabitRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class StreakRepositoryTest {

    @Autowired
    private StreakRepository streakRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HabitRepository habitRepository;

    private Streak testStreak;

    private Habit testHabit;

    private User testUser;

    private String habitId;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@email.com");
        testUser.setPassword("password");
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser = userRepository.save(testUser);

        testHabit = new Habit();
        testHabit.setName("Exercise");
        testHabit.setDescription("Daily exercise");
        testHabit.setCategory(CategoryType.OTHER);
        testHabit.setUser(testUser);
        testHabit.setCreatedAt(LocalDate.now());
        testHabit.setStartDate(LocalDate.now());
        testHabit = habitRepository.save(testHabit);
        habitId = testHabit.getId();

        testStreak = new Streak();
        testStreak.setHabitId(habitId);
        testStreak.setResetAt(LocalDate.now().minusDays(1));
        testStreak.setCurrentStreak(5);
        testStreak.setLongestStreak(100);
        testStreak.setFrequency(FrequencyType.DAILY);
        testStreak = streakRepository.save(testStreak);
    }

    @AfterEach
    void tearDown() {
        streakRepository.delete(testStreak);
        habitRepository.delete(testHabit);
        userRepository.delete(testUser);
    }

    @Test
    @DisplayName("should find by the habit id")
    void findByHabitId() {
        Streak streak = streakRepository.findByHabitId(habitId).get();

        assertThat(streak).isSameAs(testStreak);
    }

    @Test
    @DisplayName("should not find by the wrong habit id")
    void findByBadHabitId() {
        Optional<Streak> streak = streakRepository.findByHabitId("wrong_id");

        assertThat(streak).isNotPresent();
    }

    @Test
    @DisplayName("should delete all by habit id")
    void deleteAllByHabitId() {
        streakRepository.deleteAllByHabitId(habitId);

        assertThat(streakRepository.findByHabitId(habitId)).isNotPresent();
    }

    @Test
    @DisplayName("should reset missed streak")
    void resetMissedStreaks() {
        int updatedCount = streakRepository.resetMissedStreaks(10);

        assertThat(updatedCount).isEqualTo(1);
    }
}