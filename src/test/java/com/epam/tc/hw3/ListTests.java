package com.epam.tc.hw3;

import com.epam.tc.hw3.assertions.ListAssertions;
import com.epam.tc.hw3.assertions.ResponseAssertions;
import com.epam.tc.hw3.data.BaseData;
import com.epam.tc.hw3.data.TrelloDataProvider;
import com.epam.tc.hw3.dto.BoardDTO;
import com.epam.tc.hw3.dto.ListDTO;
import com.epam.tc.hw3.service.BoardSteps;
import com.epam.tc.hw3.service.ListSteps;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ListTests {

    private BoardSteps boardSteps;
    private ListSteps listSteps;
    private ListAssertions listAssertions;
    private BoardDTO board;

    @BeforeMethod
    public void setUp() {
        boardSteps = new BoardSteps();
        listSteps = new ListSteps();
        listAssertions = new ListAssertions();
        board = boardSteps.createBoard(BaseData.BOARD_NAME);
    }

    @AfterMethod
    public void tearDown() {
        boardSteps.deleteBoard(board.getId());
    }

    @Test(description = "Create a list and check it was created",
            dataProviderClass = TrelloDataProvider.class, dataProvider = "List names")
    public void createList(String listName) {
        String listId = listSteps
                .createList(listName, board)
                .getId();

        ListDTO list = listSteps.getListById(listId);
        listAssertions.verifyId(list, listId).verifyName(list, listName).verifyBoardId(list, board.getId());
    }

    @Test(description = "Create a list on a board and delete the board")
    public void deleteBoardWithList() {
        String listId = listSteps
                .createList(BaseData.LIST_NAME, board)
                .getId();
        boardSteps.deleteBoard(board.getId());

        Response list = listSteps.getListResponse(listId);
        new ResponseAssertions().verifyStatusCode(list, HttpStatus.SC_NOT_FOUND);
    }

}
