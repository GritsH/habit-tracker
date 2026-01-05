package com.grits.habittracker.repository.habit;

import com.grits.habittracker.entity.User;
import com.grits.habittracker.entity.habit.Habit;
import com.grits.habittracker.model.type.CategoryType;
import com.grits.habittracker.repository.UserRepository;
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
class HabitRepositoryTest {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRepository;

    private Habit testHabit;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("username");
        testUser.setPassword("password");
        testUser.setEmail("email@gmail.com");
        testUser.setFirstName("firstName");
        testUser.setLastName("lastName");
        testUser = userRepository.save(testUser);

        testHabit = new Habit();
        testHabit.setName("name");
        testHabit.setDescription("description");
        testHabit.setCategory(CategoryType.OTHER);
        testHabit.setVersion(0L);
        testHabit.setUser(testUser);
        testHabit.setCreatedAt(LocalDate.now());
        testHabit.setStartDate(LocalDate.now());
        testHabit = habitRepository.save(testHabit);
    }

    @AfterEach
    void tearDown() {
        habitRepository.delete(testHabit);
        userRepository.delete(testUser);
    }

    @Test
    @DisplayName("should find all by user id")
    void findAllByUserId() {
        List<Habit> habits = habitRepository.findAllByUserId(testUser.getId());

        assertThat(habits).contains(testHabit);
    }

    @Test
    @DisplayName("should not find all by the wrong user id")
    void findAllWithBadUserId() {
        List<Habit> habits = habitRepository.findAllByUserId("wrong_id");

        assertThat(habits).isEmpty();
    }

    @Test
    @DisplayName("should exist by habit and user ids")
    void existsByIdAndUserId() {
        boolean habitExists = habitRepository.existsByIdAndUserId(testHabit.getId(), testUser.getId());

        assertThat(habitExists).isTrue();
    }

    @Test
    @DisplayName("should not exist by wrong user id")
    void existsByIdAndBadUserId() {
        boolean habitExists = habitRepository.existsByIdAndUserId(testUser.getId(), "wrong_id");

        assertThat(habitExists).isFalse();
    }

    @Test
    @DisplayName("should not exist by user id and wrong habit id")
    void existsByBadIdAndUserId() {
        boolean habitExists = habitRepository.existsByIdAndUserId("wrong_id", testUser.getId());

        assertThat(habitExists).isFalse();
    }
}