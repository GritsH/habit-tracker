package com.grits.api.user;

import com.grits.api.UserOperation;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoginApiTest {

    private String validEmail;

    private final String validPassword = UserOperation.PASSWORD;

    @BeforeEach
    public void setup() {
        Response response = UserOperation.signUpUser();
        validEmail = response.jsonPath().getString("user.email");
    }

    @Test
    @DisplayName("should login a user")
    public void testLogin() {
        Response response = UserOperation.loginUser(validEmail, validPassword);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.jsonPath().getString("user.id")).isNotNull();
        assertThat(response.jsonPath().getString("user.email")).isEqualTo(validEmail);
        assertThat(response.jsonPath().getString("user.firstName")).isEqualTo("John");
        assertThat(response.jsonPath().getString("user.lastName")).isEqualTo("Doe");
        assertThat(response.jsonPath().getString("token")).isNotNull();
        assertThat(response.jsonPath().getString("tokenType")).isEqualTo("Bearer");
    }

    @Test
    @DisplayName("should not login a user with incorrect password")
    public void testLoginWithIncorrectPassword() {
        Response response = UserOperation.loginUser(validEmail, "wrongpassword");

        assertThat(response.statusCode()).isEqualTo(401);
        assertThat(response.jsonPath().getString("title")).isEqualTo("Unauthorized");
        assertThat(response.jsonPath().getString("detail")).contains("Invalid credentials");
        assertThat(response.jsonPath().getString("instance")).isEqualTo("/v1/login");
    }

    @Test
    @DisplayName("should not login a user with non-existent email")
    public void testLoginWithNonExistentEmail() {
        String nonExistentEmail = "nonexistent_" + UUID.randomUUID().toString().substring(0, 12) + "@gmail.com";
        Response response = UserOperation.loginUser(nonExistentEmail, validPassword);

        assertThat(response.statusCode()).isEqualTo(404);
        assertThat(response.jsonPath().getString("title")).isEqualTo("Not Found");
        assertThat(response.jsonPath().getString("detail")).contains("User " + nonExistentEmail + " not found");
        assertThat(response.jsonPath().getString("instance")).isEqualTo("/v1/login");
    }

    @Test
    @DisplayName("should return different tokens for multiple logins")
    public void testMultipleLoginsShouldReturnDifferentTokens() {
        Response firstResponse = UserOperation.loginUser(validEmail, validPassword);
        Response secondResponse = UserOperation.loginUser(validEmail, validPassword);

        assertThat(firstResponse.statusCode()).isEqualTo(200);
        assertThat(secondResponse.statusCode()).isEqualTo(200);
        assertNotEquals(firstResponse.jsonPath().getString("token"), secondResponse.jsonPath().getString("token"));
    }
}
