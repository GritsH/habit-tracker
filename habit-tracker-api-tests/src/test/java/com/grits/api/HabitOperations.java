package com.grits.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class HabitOperations {

    private static final String BASE_URL = "http://grits.test.habittracker.com";

    public static Response createHabit(String userId, String token) {
        RestAssured.baseURI = BASE_URL;
        String requestBody = createNewHabitRequestBody("Morning Meditation", LocalDate.now().toString(), "MENTAL_HEALTH");

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
        .when()
                .post("/v1/users/{userId}/habits", userId)
        .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("version", equalTo(0))
                .body("name", equalTo("Morning Meditation"))
                .body("description", equalTo("Description"))
                .body("category", equalTo("MENTAL_HEALTH"))
                .body("createdAt", equalTo(LocalDate.now().toString()))
                .body("startDate", equalTo(LocalDate.now().toString()))
                .extract().response();
    }

    public static Response createHabit(String userId, String token, String name, String startDate, String category) {
        RestAssured.baseURI = BASE_URL;
        String requestBody = createNewHabitRequestBody(name, startDate, category);

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
        .when()
                .post("/v1/users/{userId}/habits", userId)
        .then()
                .extract().response();
    }

    public static Response getAllHabits(String userId, String token) {
        RestAssured.baseURI = BASE_URL;
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
        .when()
                .get("/v1/users/{userId}/habits", userId)
        .then()
                .extract().response();
    }

    public static Response deleteHabit(String userId, String token, String habitId) {
        RestAssured.baseURI = BASE_URL;
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
        .when()
                .delete("/v1/users/{userId}/habits/{habitId}", userId, habitId)
        .then()
                .extract().response();
    }

    public static Response updateHabit(String userId, String token, String habitId, String requestBody) {
        RestAssured.baseURI = BASE_URL;
        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .header("Authorization", "Bearer " + token)
        .when()
                .patch("/v1/users/{userId}/habits/{habitId}", userId, habitId)
        .then()
                .extract().response();
    }

    private static String createNewHabitRequestBody(String name, String startDate, String category) {
        return """
                {
                    "name": "%s",
                    "description": "Description",
                    "startDate": "%s",
                    "frequency": "DAILY",
                    "category": "%s"
                }
                """.formatted(name, startDate, category);
    }
}
