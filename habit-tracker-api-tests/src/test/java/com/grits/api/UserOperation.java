package com.grits.api;

import com.grits.api.model.response.AuthResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.UUID;

import static com.grits.api.util.Constants.BASE_URL;
import static com.grits.api.util.Constants.FIRST_NAME;
import static com.grits.api.util.Constants.GMAIL;
import static com.grits.api.util.Constants.LAST_NAME;
import static com.grits.api.util.Constants.PASSWORD;
import static com.grits.api.util.Constants.TEST_PREFIX;
import static com.grits.api.util.Constants.TOKEN_TYPE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserOperation {

    public static AuthResponse signUpUser() {
        RestAssured.baseURI = BASE_URL;
        String testEmail = TEST_PREFIX + UUID.randomUUID().toString().substring(0, 12) + GMAIL;
        String testUsername = TEST_PREFIX + UUID.randomUUID().toString().substring(0, 12);
        String requestBody = createSignupRequestBody(testEmail, PASSWORD, testUsername);

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/v1/signup")
        .then()
                .statusCode(200)
                .body("user.id", notNullValue())
                .body("user.email", equalTo(testEmail))
                .body("user.firstName", equalTo(FIRST_NAME))
                .body("user.lastName", equalTo(LAST_NAME))
                .body("user.username", equalTo(testUsername))
                .body("token", notNullValue())
                .body("tokenType", equalTo(TOKEN_TYPE))
                .extract().response().as(AuthResponse.class);
    }

    public static Response signUpUser(String email, String password, String username) {
        RestAssured.baseURI = BASE_URL;
        String requestBody = createSignupRequestBody(email, password, username);

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
        .when()
                .post("/v1/signup")
        .then()
                .extract().response();
    }

    public static AuthResponse loginUser() {
        RestAssured.baseURI = BASE_URL;
        AuthResponse signedUpUser = signUpUser();
        String validEmail = signedUpUser.getUser().getEmail();

        String loginRequest = createLoginRequestBody(validEmail, PASSWORD);

        return given()
                .contentType("application/json")
                .body(loginRequest)
        .when()
                .post("/v1/login")
        .then()
                .statusCode(200)
                .body("user.id", notNullValue())
                .body("user.email", equalTo(validEmail))
                .body("user.firstName", equalTo(FIRST_NAME))
                .body("user.lastName", equalTo(LAST_NAME))
                .body("token", notNullValue())
                .body("tokenType", equalTo(TOKEN_TYPE))
                .extract().response().as(AuthResponse.class);
    }

    public static Response loginUser(String email, String password) {
        RestAssured.baseURI = BASE_URL;
        String loginRequest = createLoginRequestBody(email, password);

        return given()
                .contentType("application/json")
                .body(loginRequest)
        .when()
                .post("/v1/login")
        .then()
                .extract().response();
    }

    public static Response logoutUser(String token) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", TOKEN_TYPE + " " + token)
        .when()
                .post("/v1/logout")
        .then()
                .extract().response();
    }

    public static Response getUser(String token, String userId) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", TOKEN_TYPE + " " + token)
        .when()
                .get("/v1/users/{id}", userId)
        .then()
                .extract().response();
    }

    private static String createLoginRequestBody(String email, String password) {
        return """
            {
                "email": "%s",
                "password": "%s"
            }
            """.formatted(email, password);
    }

    private static String createSignupRequestBody(String email, String password, String username) {
        return """
                {
                    "email": "%s",
                    "password": "%s",
                    "firstName": "%s",
                    "lastName": "%s",
                    "username": "%s"
                }
                """.formatted(email, password, FIRST_NAME, LAST_NAME, username);
    }
}
