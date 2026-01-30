package com.grits.api.habit;

import com.grits.api.HabitOperations;
import com.grits.api.UserOperation;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateHabitApiTest {

    private static String testUserId;

    private static String testUserToken;

    private static String testHabitId;

    @BeforeEach
    public void setup() {
        Response loginResponse = UserOperation.loginUser();

        testUserId = loginResponse.path("user.id");
        testUserToken = loginResponse.path("token");

        Response createHabitResponse = HabitOperations.createHabit(testUserId, testUserToken);
        testHabitId = createHabitResponse.path("id");
    }

    @Test
    @DisplayName("should update a habit")
    public void testUpdateHabit() {
        String requestBody = createUpdateHabitRequestBody();
        Response response = HabitOperations.updateHabit(testUserId, testUserToken, testHabitId, requestBody);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("id")).isEqualTo(testHabitId);
        assertThat(response.jsonPath().getString("name")).isEqualTo("Updated habit");
        assertThat(response.jsonPath().getString("description")).isEqualTo("Updated description");
        assertThat(response.jsonPath().getString("startDate")).isEqualTo(LocalDate.now().plusDays(1).toString());
        assertThat(response.jsonPath().getString("category")).isEqualTo("HOME");
    }

    @Test
    @DisplayName("should not update a habit with invalid token")
    public void testUpdateHabitWithInvalidToken() {
        String requestBody = createUpdateHabitRequestBody();
        Response response = HabitOperations.updateHabit(testUserId, "invalidToken", testHabitId, requestBody);

        assertThat(response.statusCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("should not update a habit with valid token but different user id")
    public void testUpdateHabitWithValidTokenAndDifferentUserId() {
        String requestBody = createUpdateHabitRequestBody();
        Response response = HabitOperations.updateHabit(UUID.randomUUID().toString(), testUserToken, testHabitId, requestBody);

        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(response.jsonPath().getString("detail")).contains("Access Denied");
    }

    @Test
    @DisplayName("should not update a habit with invalid habit id")
    public void testUpdateHabitWithInvalidHabitId() {
        String requestBody = createUpdateHabitRequestBody();
        String invalidHabitId = UUID.randomUUID().toString();
        Response response = HabitOperations.updateHabit(testUserId, testUserToken, invalidHabitId, requestBody);

        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.jsonPath().getString("detail")).contains("Habit with id " + invalidHabitId + " not found");
    }

    private String createUpdateHabitRequestBody() {
        return """
                {
                    "name": "Updated habit",
                    "description": "Updated description",
                    "startDate": "%s",
                    "frequency": "EVERY_TWO_DAYS",
                    "category": "HOME"
                }
                """.formatted(LocalDate.now().plusDays(1));
    }
}
