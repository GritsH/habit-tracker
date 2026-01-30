package com.grits.api.user;

import com.grits.api.UserOperation;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class SignUpApiTest {

    private String testUsername;

    private String testEmail;

    private final String testPassword = UserOperation.PASSWORD;

    @BeforeEach
    public void setup() {
        testUsername = "test_" + UUID.randomUUID().toString().substring(0, 12);
        testEmail = "test_" + UUID.randomUUID().toString().substring(0, 12) + "@gmail.com";
    }

    @Test
    @DisplayName("should sign up a new user")
    public void testSignUp() {
        UserOperation.signUpUser();
    }

    @ParameterizedTest
    @DisplayName("should not sign up a new user with invalid email")
    @ValueSource(strings = {
            "111",
            "in val id@",
            "@domain.com",
            "invalid@@domain@.com",
            "invalid@domain..com",
            "   "
    })
    public void testInvalidEmail(String invalidEmail) {
        Response response = UserOperation.signUpUser(invalidEmail, testPassword, testUsername);

        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.jsonPath().getString("title")).isEqualTo("Bad Request");
        assertThat(response.jsonPath().getString("detail")).contains("email");
        assertThat(response.jsonPath().getString("detail")).contains("must be a well-formed email address");
        assertThat(response.jsonPath().getString("instance")).isEqualTo("/v1/signup");
    }

    @Test
    @DisplayName("should not sign up a new user with null email")
    public void testSignUpWithNullEmail() {
        Response response = UserOperation.signUpUser(null, testPassword, testUsername);

        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("should not sign up a new user with blank password")
    public void testSignUpWithBlankPassword() {
        Response responseWithEmptyPassword = UserOperation.signUpUser(testEmail, "", testUsername);

        assertThat(responseWithEmptyPassword.statusCode()).isEqualTo(400);
    }

    @Test
    @DisplayName("should not sign up a new user with blank username")
    public void testSignUpWithBlankUsername() {
        Response responseWithEmptyUsername = UserOperation.signUpUser(testEmail, testPassword, "");

        assertThat(responseWithEmptyUsername.statusCode()).isEqualTo(400);
    }
}
