package com.grits.api.habit;

import com.grits.api.HabitOperations;
import com.grits.api.UserOperation;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteHabitApiTest {

    private String testUserId;

    private String testUserToken;

    private String testHabitId;

    @BeforeEach
    public void setup() {
        Response loginResponse = UserOperation.loginUser();

        testUserId = loginResponse.path("user.id");
        testUserToken = loginResponse.path("token");

        Response createHabitResponse = HabitOperations.createHabit(testUserId, testUserToken);
        testHabitId = createHabitResponse.path("id");
    }

    @Test
    @DisplayName("should delete a habit")
    public void testDeleteHabit() {
        Response response = HabitOperations.deleteHabit(testUserId, testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(204);
    }

    @Test
    @DisplayName("should not delete a habit with invalid token")
    public void testDeleteHabitWithInvalidToken() {
        Response response = HabitOperations.deleteHabit(testUserId, "invalid_token", testHabitId);

        assertThat(response.statusCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("should not delete a habit with valid token and different user id")
    public void testDeleteHabitWithInvalidUserId() {
        Response response = HabitOperations.deleteHabit(UUID.randomUUID().toString(), testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(response.jsonPath().getString("detail")).contains("Access Denied");
    }
}
