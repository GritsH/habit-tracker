package com.grits.api.user;

import com.grits.api.UserOperation;
import com.grits.api.model.response.AuthResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.grits.api.util.Constants.FIRST_NAME;
import static com.grits.api.util.Constants.LAST_NAME;
import static org.assertj.core.api.Assertions.assertThat;

public class GetUserApiTest {

    private String testEmail;

    private String testUsername;

    private String testUserId;

    private String testUserToken;

    @BeforeEach
    public void setup() {
        AuthResponse loginResponse = UserOperation.loginUser();

        testUserId = loginResponse.getUser().getId();
        testEmail = loginResponse.getUser().getEmail();
        testUsername = loginResponse.getUser().getUsername();
        testUserToken = loginResponse.getToken();
    }

    @Test
    @DisplayName("should get user by id with valid token")
    public void testGetUserByIdWithValidToken() {
        Response response = UserOperation.getUser(testUserToken, testUserId);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("id")).isEqualTo(testUserId);
        assertThat(response.jsonPath().getString("email")).isEqualTo(testEmail);
        assertThat(response.jsonPath().getString("firstName")).isEqualTo(FIRST_NAME);
        assertThat(response.jsonPath().getString("lastName")).isEqualTo(LAST_NAME);
        assertThat(response.jsonPath().getString("username")).isEqualTo(testUsername);
        assertThat(response.jsonPath().getString("password")).isNull();
        assertThat(response.jsonPath().getString("token")).isNull();
        assertThat(response.jsonPath().getString("tokenType")).isNull();
    }

    @Test
    @DisplayName("should not get user by id without authentication")
    public void testGetUserByIdWithoutAuthentication() {
        Response response = UserOperation.getUser(null, testUserId);

        assertThat(response.statusCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("should not get user by id with invalid token")
    public void testGetUserByIdWithInvalidToken() {
        Response response = UserOperation.getUser("invalid_token", testUserId);

        assertThat(response.statusCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("should not get user by id with valid token but different id")
    public void testGetUserByIdWithValidTokenButDifferentId() {
        String differentUserId = UUID.randomUUID().toString();

        Response response = UserOperation.getUser(testUserToken, differentUserId);

        assertThat(response.statusCode()).isEqualTo(500);
        assertThat(response.jsonPath().getString("detail")).contains("Access Denied");
        assertThat(response.jsonPath().getString("instance")).isEqualTo("/v1/users/" + differentUserId);
    }
}
