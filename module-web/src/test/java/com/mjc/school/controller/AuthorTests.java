package com.mjc.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static com.mjc.school.controller.TestHelper.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class AuthorTests {

    @Test
    public void createAuthorTest() {
        createAuthor(DEFAULT_AUTHOR_NAME);
    }

    @Test
    public void readAuthorByIdTest() {
        var id = createAuthor(DEFAULT_AUTHOR_NAME);

        var reqBody = new JSONObject();
        reqBody.put("id", id);

        given().contentType("application/json").body(reqBody.toString())
                .when().get(AUTHORS + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(DEFAULT_AUTHOR_NAME))
                .body("id", equalTo(id));

    }

    @Test
    public void readAllAuthorsTest() {
        createAuthor(DEFAULT_AUTHOR_NAME);
        createAuthor(DEFAULT_AUTHOR_NAME);

        given().contentType("application/json")
                .when().get(AUTHORS)
                .then()
                .statusCode(200)
                .body("data", hasSize(greaterThanOrEqualTo(2)));
    }

    @Test
    public void updateAuthorTest() {
        String authorNameUpdated = DEFAULT_AUTHOR_NAME + "Updated";

        var id = createAuthor(DEFAULT_AUTHOR_NAME);

        var reqBody = new JSONObject();
        reqBody.put("name", authorNameUpdated);
        reqBody.put("id", id);

        given().contentType("application/json").body(reqBody.toString())
                .when().patch(AUTHORS + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(authorNameUpdated));

    }

    @Test
    public void updateAuthorTestInvalid() {
        var id = createAuthor(DEFAULT_AUTHOR_NAME);

        var reqBodyInvalid = new JSONObject();
        reqBodyInvalid.put("name", "a");
        reqBodyInvalid.put("id", id);

        given().contentType("application/json").body(reqBodyInvalid.toString())
                .when().patch(AUTHORS + id)
                .then()
                .statusCode(400);
    }

    @Test
    public void deleteAuthorTest() {
        var id = createAuthor(DEFAULT_AUTHOR_NAME);
        given()
                .when().delete(AUTHORS + id)
                .then()
                .statusCode(204);
    }
}
