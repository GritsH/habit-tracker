package com.grits.api.habit;

import com.grits.api.HabitOperations;
import com.grits.api.UserOperation;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class LogCompletionApiTest {

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
    @DisplayName("should log completion for a habit")
    public void testLogCompletion() {
        Response response = HabitOperations.logCompletion(testUserId, testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("completionLog")).contains(LocalDate.now().toString());
        assertThat(response.jsonPath().getString("loggedAt")).isEqualTo(LocalDate.now().toString());
    }

    @Test
    @DisplayName("should log habit completion only once")
    public void testLogCompletionSeveralTimes() {
        Response response = HabitOperations.logCompletion(testUserId, testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("completionLog")).contains(LocalDate.now().toString());
        assertThat(response.jsonPath().getString("loggedAt")).isEqualTo(LocalDate.now().toString());

        response = HabitOperations.logCompletion(testUserId, testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(409);
        assertThat(response.jsonPath().getString("detail")).contains("already logged as completed");
    }

    @Test
    @DisplayName("should not log completion of invalid habit")
    public void testLogCompletionWithInvalidHabitId() {
        Response response = HabitOperations.logCompletion(testUserId, testUserToken, UUID.randomUUID().toString());

        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.jsonPath().getString("detail")).isEqualTo("No information could be retrieved");
    }

    @Test
    @DisplayName("should not log completion of invalid token")
    public void testLogCompletionWithInvalidToken() {
        Response response = HabitOperations.logCompletion(testUserId, "invalid_token", testHabitId);

        assertThat(response.statusCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("should get all logs for habit")
    public void testGetAllHabitCompletions() {
        HabitOperations.logCompletion(testUserId, testUserToken, testHabitId);
        Response response = HabitOperations.getLogHistory(testUserId, testUserToken, testHabitId);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getList("").size()).isGreaterThan(0);
    }
}
