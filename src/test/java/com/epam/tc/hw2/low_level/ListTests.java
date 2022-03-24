package com.epam.tc.hw2.low_level;

import static com.epam.tc.hw2.data.CommonData.*;
import static com.epam.tc.hw2.data.TrelloURL.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ListTests {

    private final String NEW_LIST_NAME = "new list name";

    private String boardId;
    private Response createdListResponse;
    private String listId;

    @BeforeMethod
    public void setUp() {
        Response createdBoardResponse = given(REQUEST_SPECIFICATION)
                .queryParam("name", BOARD_NAME)
                .post(BOARDS_END_POINT);
        boardId = createdBoardResponse.then().extract().path("id");
        createdListResponse = given(REQUEST_SPECIFICATION)
                .queryParams("idBoard", boardId,
                        "name", LIST_NAME)
                .post(LISTS_END_POINT);
        listId = createdListResponse.then().extract().path("id");
    }

    @AfterMethod
    public void tearDown() {
        given(REQUEST_SPECIFICATION).delete(USER_BOARDS_END_POINT, boardId);
    }

    @Test(description = "Create a list")
    public void createList() {
        createdListResponse
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .contentType(containsString("charset=utf-8"));

        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_LISTS_END_POINT, listId)
                .then()
                    .body("idBoard", is(boardId),
                        "id", is(listId),
                        "name", is(LIST_NAME));
    }

    @Test(description = "Update list name")
    public void updateList() {
        given(REQUEST_SPECIFICATION)
                .when()
                    .queryParam("name", NEW_LIST_NAME)
                    .put(USER_LISTS_END_POINT, listId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .header("Access-Control-Allow-Methods", matchesPattern(".*PUT.*"));

        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_LISTS_END_POINT, listId)
                .then()
                    .body("id", is(listId),
                        "name", is(NEW_LIST_NAME));
    }

    @Test(description = "Get a list by id")
    public void getList() {
        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_LISTS_END_POINT, listId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .body("idBoard", is(boardId),
                            "id", is(listId),
                            "name", is(LIST_NAME));
    }

    @Test(description = "Archive list")
    public void archiveList() {
        given(REQUEST_SPECIFICATION)
                .when()
                    .queryParam("value", "true")
                    .put(LISTS_ARCHIVE_END_POINT, listId)
                .then()
                    .statusCode(HttpStatus.SC_OK);

        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_LISTS_END_POINT, listId)
                .then()
                    .body("id", is(listId),
                        "closed", is(true));
    }

}
