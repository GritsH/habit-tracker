package com.grits.api.habit;

import com.grits.api.HabitOperations;
import com.grits.api.UserOperation;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class CreateHabitApiTest {

    private String testUserId;

    private String testUserToken;

    @BeforeEach
    public void setup() {
        Response loginResponse = UserOperation.loginUser();

        testUserId = loginResponse.path("user.id");
        testUserToken = loginResponse.path("token");
    }

    @Test
    @DisplayName("should create a new habit")
    public void testCreateHabit() {
        HabitOperations.createHabit(testUserId, testUserToken);
    }

    @Test
    @DisplayName("should not create a new habit without authentication")
    public void testCreateHabitWithoutAuthentication() {
        Response response = HabitOperations.createHabit(testUserId, null, "Habit Name", LocalDate.now().toString(), "OTHER");

        assertThat(response.statusCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("should not create a new habit with valid token for different user")
    public void testCreateHabitWithValidTokenForDifferentUser() {
        Response response = HabitOperations.createHabit(UUID.randomUUID().toString(), testUserToken, "Habit Name", LocalDate.now().toString(), "OTHER");

        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(response.jsonPath().getString("detail")).contains("Access Denied");
    }

    @Test
    @DisplayName("should not create a new habit with past start date")
    public void testCreateHabitWithPastStartDate() {
        String pastDate = LocalDate.now().minusDays(10).toString();
        Response response = HabitOperations.createHabit(testUserId, testUserToken, "Habit Name", pastDate, "OTHER");

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.jsonPath().getString("detail")).contains("startDate: must be a date in the present or in the future");
        assertThat(response.jsonPath().getString("instance")).isEqualTo("/v1/users/" + testUserId + "/habits");
    }

    @ParameterizedTest
    @DisplayName("should not create a new habit with invalid name")
    @ValueSource(strings = {
            "a",
            "this name is more that thirty characters long!!"
    })
    public void testCreateHabitWithInvalidName(String invalidName) {
        Response response = HabitOperations.createHabit(testUserId, testUserToken, invalidName, LocalDate.now().toString(), "OTHER");

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.jsonPath().getString("detail")).contains("name: Name must be 2-30 characters long");
        assertThat(response.jsonPath().getString("instance")).isEqualTo("/v1/users/" + testUserId + "/habits");
    }

    @ParameterizedTest
    @DisplayName("should not create a new habit with blank name")
    @ValueSource(strings = {
            "",
            "    "
    })
    public void testCreateHabitWithBlankName(String invalidName) {
        Response response = HabitOperations.createHabit(testUserId, testUserToken, invalidName, LocalDate.now().toString(), "OTHER");

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.jsonPath().getString("detail")).contains("name: must not be blank");
        assertThat(response.jsonPath().getString("instance")).isEqualTo("/v1/users/" + testUserId + "/habits");
    }
}
