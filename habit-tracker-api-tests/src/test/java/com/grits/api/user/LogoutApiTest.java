package com.grits.api.user;

import com.grits.api.UserOperation;
import com.grits.api.model.response.AuthResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LogoutApiTest {

    private String testUserToken;

    @BeforeEach
    public void setUp() {
        AuthResponse loginResponse = UserOperation.loginUser();

        testUserToken = loginResponse.getToken();
    }

    @Test
    @DisplayName("should logout a user with valid token")
    public void testLogout() {
        Response response = UserOperation.logoutUser(testUserToken);

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("should return 403 when logging out with the same token twice")
    public void testLogoutWithValidToken() {
        assertThat(UserOperation.logoutUser(testUserToken).statusCode()).isEqualTo(200);
        assertThat(UserOperation.logoutUser(testUserToken).statusCode()).isEqualTo(403);
    }

    @Test
    @DisplayName("should return 403 when logging out a user with invalid token")
    public void testLogoutWithInvalidToken() {
        assertThat(UserOperation.logoutUser("invalid_token").statusCode()).isEqualTo(403);
    }
}
