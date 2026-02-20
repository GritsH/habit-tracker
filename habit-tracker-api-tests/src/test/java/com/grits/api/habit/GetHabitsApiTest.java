package com.grits.api.habit;

import com.grits.api.HabitOperations;
import com.grits.api.UserOperation;
import com.grits.api.model.response.AuthResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class GetHabitsApiTest {

    private String testUserId;

    private String testUserToken;

    @BeforeEach
    public void setup() {
        AuthResponse loginResponse = UserOperation.loginUser();

        testUserId = loginResponse.getUser().getId();
        testUserToken = loginResponse.getToken();
        HabitOperations.createHabit(testUserId, testUserToken);
    }

    @Test
    @DisplayName("should get all habits for a user with valid token")
    public void testGetAllHabitsWithValidToken() {
        Response response = HabitOperations.getAllHabits(testUserId, testUserToken);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getList("").size()).isGreaterThan(0);
    }

    @Test
    @DisplayName("should not get all habits for a user with invalid token")
    public void testGetAllHabitsWithInvalidToken() {
        Response response = HabitOperations.getAllHabits(testUserId, "invalid_token");

        assertThat(response.statusCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("should not get all habits with valid token and different user id")
    public void testGetAllHabitsWithValidTokenAndDifferentUserId() {
        Response response = HabitOperations.getAllHabits(UUID.randomUUID().toString(), testUserToken);

        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(response.jsonPath().getString("detail")).contains("Access Denied");
    }
}
