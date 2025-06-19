package com.devt.blogger.utils.rest;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class RestAssuredHelper {

    private RestAssuredHelper() {
        // utility class does not need public constructor
    }

    public static ValidatableResponse get(String url) {
        return given()
                .when()
                .get(url)
                .then();

    }

    public static ValidatableResponse post(String url, Object body) {
        return given()
                .contentType(ContentType.JSON)
                .body(Json.withObject(body).stringify())
                .when()
                .post(url)
                .then();
    }

    public static ValidatableResponse patch(String url) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .patch(url)
                .then();
    }

}