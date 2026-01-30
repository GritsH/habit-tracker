package com.grits.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserOperation {

    private static final String BASE_URL = "http://grits.test.habittracker.com";

    public static final String PASSWORD = "123password";

    public static Response signUpUser() {
        RestAssured.baseURI = BASE_URL;
        String testEmail = "test_" + UUID.randomUUID().toString().substring(0, 12) + "@gmail.com";
        String testUsername = "test_" + UUID.randomUUID().toString().substring(0, 12);
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
                .body("user.firstName", equalTo("John"))
                .body("user.lastName", equalTo("Doe"))
                .body("user.username", equalTo(testUsername))
                .body("token", notNullValue())
                .body("tokenType", equalTo("Bearer"))
                .extract().response();
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

    public static Response loginUser() {
        RestAssured.baseURI = BASE_URL;
        Response signedUpUser = signUpUser();
        String validEmail = signedUpUser.jsonPath().getString("user.email");

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
                .body("user.firstName", equalTo("John"))
                .body("user.lastName", equalTo("Doe"))
                .body("token", notNullValue())
                .body("tokenType", equalTo("Bearer"))
                .extract().response();
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
                .header("Authorization", "Bearer " + token)
        .when()
                .post("/v1/logout")
        .then()
                .extract().response();
    }

    public static Response getUser(String token, String userId) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
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
                    "firstName": "John",
                    "lastName": "Doe",
                    "username": "%s"
                }
                """.formatted(email, password, username);
    }
}
