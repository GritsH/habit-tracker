package com.grits.api.habit;

import com.grits.api.HabitOperations;
import com.grits.api.UserOperation;
import com.grits.api.model.response.AuthResponse;
import com.grits.api.model.response.HabitResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class StreakApiTest {

    private String testUserId;

    private String testUserToken;

    private String testHabitId;

    @BeforeEach
    public void setup() {
        AuthResponse loginResponse = UserOperation.loginUser();

        testUserId = loginResponse.getUser().getId();
        testUserToken = loginResponse.getToken();

        HabitResponse createHabitResponse = HabitOperations.createHabit(testUserId, testUserToken);
        testHabitId = createHabitResponse.getId();
    }

    @Test
    @DisplayName("should create empty streak on habit creation")
    public void testStreakCreation() {
        Response response = HabitOperations.getStreak(testUserId, testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("currentStreak")).isEqualTo("0");
        assertThat(response.jsonPath().getString("longestStreak")).isEqualTo("0");
        assertThat(response.jsonPath().getString("habitId")).isEqualTo(testHabitId);
        assertThat(response.jsonPath().getString("resetAt")).isEqualTo(null);
    }

    @Test
    @DisplayName("should increment streak on logging completion")
    public void testStreakIncrement() {
        HabitOperations.logCompletion(testUserId, testUserToken, testHabitId);
        Response response = HabitOperations.getStreak(testUserId, testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("currentStreak")).isEqualTo("1");
        assertThat(response.jsonPath().getString("longestStreak")).isEqualTo("1");
        assertThat(response.jsonPath().getString("habitId")).isEqualTo(testHabitId);
    }

    @Test
    @DisplayName("should update frequency on habit update")
    public void testStreakUpdate() {
        String requestBody = """
                {
                    "name": "Updated habit",
                    "description": "Updated description",
                    "startDate": "%s",
                    "frequency": "EVERY_TWO_DAYS",
                    "category": "HOME"
                }
                """.formatted(LocalDate.now().plusDays(1));

        HabitOperations.updateHabit(testUserId, testUserToken, testHabitId, requestBody);
        Response response = HabitOperations.getStreak(testUserId, testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("habitId")).isEqualTo(testHabitId);
        assertThat(response.jsonPath().getString("frequency")).isEqualTo("EVERY_TWO_DAYS");
    }
}
