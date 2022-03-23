package com.epam.tc.hw2.low_level;

import static com.epam.tc.hw2.data.CommonData.BOARD_NAME;
import static com.epam.tc.hw2.data.CommonData.REQUEST_SPECIFICATION;
import static com.epam.tc.hw2.data.TrelloURL.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BoardTests {

    private final String BOARD_DESCRIPTION = "board description";

    private Response createdBoardResponse;
    private String boardId;

    @BeforeMethod
    public void setUp() {
        createdBoardResponse = given(REQUEST_SPECIFICATION)
                .queryParam("name", BOARD_NAME)
                .post(BOARDS_END_POINT);
        boardId = createdBoardResponse.then().extract().path("id");
    }

    @AfterMethod
    public void tearDown() {
        given(REQUEST_SPECIFICATION).delete(USER_BOARDS_END_POINT, boardId);
    }

    @Test(description = "Create a board")
    public void createBoard() {
        createdBoardResponse
                .then()
                    .statusCode(HttpStatus.SC_OK);

        // check board exists
        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_BOARDS_END_POINT, boardId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .body("id", is(boardId),
                            "name", is(BOARD_NAME));
    }

    @Test(description = "Delete a board")
    public void deleteBoard() {
        given(REQUEST_SPECIFICATION)
                .when()
                    .delete(USER_BOARDS_END_POINT, boardId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .body("_value", nullValue());

        // check board doesn't exist
        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_BOARDS_END_POINT, boardId)
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(description = "Get a board by id")
    public void getCreatedBoardById() {
        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_BOARDS_END_POINT, boardId)
                .then()
                    .contentType(ContentType.JSON)
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .body("id", is(boardId),
                            "name", is(BOARD_NAME));
    }

    @Test(description = "Change description of the board")
    public void updateBoard() {
        given(REQUEST_SPECIFICATION)
                .when()
                    .queryParam("desc", BOARD_DESCRIPTION)
                    .put(USER_BOARDS_END_POINT, boardId)
                .then()
                    .statusCode(HttpStatus.SC_OK);

        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_BOARDS_END_POINT, boardId)
                .then()
                    .statusLine("HTTP/1.1 200 OK")
                .and()
                    .body("id", is(boardId),
                        "desc", is(BOARD_DESCRIPTION));
    }

}
