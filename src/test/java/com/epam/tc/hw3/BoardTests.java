package com.epam.tc.hw3;

import com.epam.tc.hw3.assertions.BoardAssertions;
import com.epam.tc.hw3.assertions.ResponseAssertions;
import com.epam.tc.hw3.dto.BoardDTO;
import com.epam.tc.hw3.data.TrelloDataProvider;
import com.epam.tc.hw3.service.BoardSteps;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BoardTests {

    private BoardSteps boardSteps;
    private BoardAssertions boardAssertions;
    private String boardId;

    @BeforeMethod
    public void setUp() {
        boardSteps = new BoardSteps();
        boardAssertions = new BoardAssertions();
    }

    @AfterMethod
    public void tearDown() {
        boardSteps.deleteBoard(boardId);
    }

    @Test(description = "Create a board and check it was created",
            dataProviderClass = TrelloDataProvider.class, dataProvider = "Board names")
    public void createBoard(String name) {
        boardId = boardSteps.createBoard(name).getId();
        BoardDTO board = boardSteps.getBoardById(boardId);
        boardAssertions.verifyId(board, boardId).verifyName(board, name);
    }

    @Test(description = "Delete a board and check it was deleted",
            dataProviderClass = TrelloDataProvider.class, dataProvider = "Board names")
    public void deleteBoard(String name) {
        boardId = boardSteps.createBoard(name).getId();
        Response response = boardSteps.deleteBoard(boardId);
        boardAssertions.verifyDeletedBoard(response);

        Response getBoard = boardSteps.getBoardResponse(boardId);
        new ResponseAssertions().verifyStatusCode(getBoard, HttpStatus.SC_NOT_FOUND);
    }

    @Test(description = "Delete a board twice",
            dataProviderClass = TrelloDataProvider.class, dataProvider = "Board names")
    public void deleteBoardTwice(String name) {
        boardId = boardSteps.createBoard(name).getId();
        boardSteps.deleteBoard(boardId);

        Response response = boardSteps.deleteBoard(boardId);
        new ResponseAssertions().verifyStatusCode(response, HttpStatus.SC_NOT_FOUND);
    }
}
