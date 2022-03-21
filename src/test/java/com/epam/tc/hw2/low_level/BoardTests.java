package com.epam.tc.hw2.low_level;

import static com.epam.tc.hw2.data.CommonData.BOARD_NAME;
import static org.hamcrest.Matchers.*;

import com.epam.tc.hw2.utils.BoardUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BoardTests {

    private final String BOARD_DESCRIPTION = "board description";

    private BoardUtils boardUtils;

    private Response createdBoardResponse;
    private String createdBoardId;

    @BeforeMethod
    public void setUp() {
        boardUtils = new BoardUtils();
        createdBoardResponse = boardUtils.createBoardRequest(BOARD_NAME);
        createdBoardId = boardUtils.getBoardId(createdBoardResponse);
    }

    @AfterMethod
    public void tearDown() {
        boardUtils.deleteBoardRequest(createdBoardId);
    }

    @Test(description = "Create a board")
    public void createBoard() {
        createdBoardResponse.then()
                .statusCode(HttpStatus.SC_OK);

        Response getBoardResponse = boardUtils.getBoardByIdRequest(createdBoardId);
        boardUtils.checkGetBody(getBoardResponse, createdBoardId, BOARD_NAME);
    }

    @Test(description = "Delete a board")
    public void deleteBoard() {
        boardUtils.deleteBoardRequest(createdBoardId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("_value", nullValue());

        boardUtils.getBoardByIdRequest(createdBoardId).then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(description = "Get a board by id")
    public void getCreatedBoardById() {
        Response getBoardResponse = boardUtils.getBoardByIdRequest(createdBoardId);
        boardUtils.checkGetBody(getBoardResponse, createdBoardId, BOARD_NAME)
                .and()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(description = "Change description of the board")
    public void updateBoard() {
        boardUtils.updateBoardByIdRequest(createdBoardId, BOARD_DESCRIPTION)
                .then()
                .statusCode(HttpStatus.SC_OK);

        Response getBoardResponse = boardUtils.getBoardByIdRequest(createdBoardId);
        boardUtils.checkGetBody(getBoardResponse, createdBoardId, BOARD_NAME)
                .and()
                .body("desc", is(BOARD_DESCRIPTION));
    }
}
