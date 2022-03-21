package com.epam.tc.hw2.low_level;

import com.epam.tc.hw2.utils.BoardUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*;

public class BoardTests {

    private final String BOARD_NAME = "board";
    private Response createdBoardResponse;
    private final String BOARD_DESCRIPTION = "board description";

    private BoardUtils boardUtils;

    @BeforeMethod
    public void setUp() {
        boardUtils = new BoardUtils();
        createdBoardResponse = boardUtils.createBoard(BOARD_NAME);
    }

    @Test(description = "create a board with name and check status")
    public void createBoard() {
        createdBoardResponse.then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", is(BOARD_NAME));
        boardUtils.deleteBoard(boardUtils.getBoardId(createdBoardResponse));
    }

    @Test(description = "delete a board and check status")
    public void deleteBoard() {
        boardUtils.deleteBoard(boardUtils.getBoardId(createdBoardResponse))
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("_value", nullValue());
    }

    @Test(description = "get a board by id")
    public void getCreatedBoardById() {
        String boardId = boardUtils.getBoardId(createdBoardResponse);

        boardUtils.getBoardById(boardId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", is(BOARD_NAME))
                .body("id", is(boardId));

        boardUtils.deleteBoard(boardId);
    }

    @Test(description = "change description of the board")
    public void updateBoard() {
        String boardId = createdBoardResponse.then().extract().path("id");

        boardUtils.updateBoardById(boardId, BOARD_DESCRIPTION)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", is(BOARD_NAME))
                .body("id", is(boardId))
                .body("desc", is(BOARD_DESCRIPTION));

        boardUtils.deleteBoard(boardId);
    }
}
