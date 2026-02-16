package com.grits.api;

import com.grits.api.model.response.HabitResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.LocalDate;

import static com.grits.api.util.Constants.BASE_URL;
import static com.grits.api.util.Constants.HABIT_CATEGORY;
import static com.grits.api.util.Constants.HABIT_DESCRIPTION;
import static com.grits.api.util.Constants.HABIT_NAME;
import static com.grits.api.util.Constants.TOKEN_TYPE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class HabitOperations {

    public static HabitResponse createHabit(String userId, String token) {
        RestAssured.baseURI = BASE_URL;
        String requestBody = createNewHabitRequestBody(HABIT_NAME, LocalDate.now().toString(), HABIT_CATEGORY);

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", TOKEN_TYPE + " " + token)
                .body(requestBody)
        .when()
                .post("/v1/users/{userId}/habits", userId)
        .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("version", equalTo(0))
                .body("name", equalTo(HABIT_NAME))
                .body("description", equalTo(HABIT_DESCRIPTION))
                .body("category", equalTo(HABIT_CATEGORY))
                .body("createdAt", equalTo(LocalDate.now().toString()))
                .body("startDate", equalTo(LocalDate.now().toString()))
                .extract().response().as(HabitResponse.class);
    }

    public static Response createHabit(String userId, String token, String name, String startDate, String category) {
        RestAssured.baseURI = BASE_URL;
        String requestBody = createNewHabitRequestBody(name, startDate, category);

        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", TOKEN_TYPE + " " + token)
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
                .header("Authorization", TOKEN_TYPE + " " + token)
        .when()
                .get("/v1/users/{userId}/habits", userId)
        .then()
                .extract().response();
    }

    public static Response deleteHabit(String userId, String token, String habitId) {
        RestAssured.baseURI = BASE_URL;
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", TOKEN_TYPE + " " + token)
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
                .header("Authorization", TOKEN_TYPE + " " + token)
        .when()
                .patch("/v1/users/{userId}/habits/{habitId}", userId, habitId)
        .then()
                .extract().response();
    }

    public static Response logCompletion(String userId, String token, String habitId) {
        RestAssured.baseURI = BASE_URL;
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", TOKEN_TYPE + " " + token)
        .when()
                .post("/v1/users/{userId}/habits/{habitId}/completions", userId, habitId)
        .then()
                .extract().response();
    }

    public static Response getLogHistory(String userId, String token, String habitId) {
        RestAssured.baseURI = BASE_URL;
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", TOKEN_TYPE + " " + token)
        .when()
                .get("/v1/users/{userId}/habits/{habitId}/completions", userId, habitId)
        .then()
                .extract().response();
    }

    public static Response getStreak(String userId, String token, String habitId) {
        RestAssured.baseURI = BASE_URL;
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", TOKEN_TYPE + " " + token)
        .when()
                .get("/v1/users/{userId}/habits/{habitId}/streak", userId, habitId)
        .then()
                .extract().response();
    }

    private static String createNewHabitRequestBody(String name, String startDate, String category) {
        return """
                {
                    "name": "%s",
                    "description": "%s",
                    "startDate": "%s",
                    "frequency": "DAILY",
                    "category": "%s"
                }
                """.formatted(name,HABIT_DESCRIPTION, startDate, category);
    }
}
