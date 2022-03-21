package com.epam.tc.hw2.low_level;

import static com.epam.tc.hw2.data.CommonData.BOARD_NAME;
import static com.epam.tc.hw2.data.CommonData.LIST_NAME;
import static org.hamcrest.Matchers.is;

import com.epam.tc.hw2.utils.BoardUtils;
import com.epam.tc.hw2.utils.ListUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ListTests {

    private final String NEW_LIST_NAME = "new list name";

    private BoardUtils boardUtils;
    private ListUtils listUtils;

    private Response createdBoardResponse;
    private Response createdListResponse;
    private String boardId;
    private String listId;

    @BeforeMethod
    public void setUp() {
        boardUtils = new BoardUtils();
        createdBoardResponse = boardUtils.createBoardRequest(BOARD_NAME);
        boardId = boardUtils.getBoardId(createdBoardResponse);
        listUtils = new ListUtils();
        createdListResponse = listUtils.createListRequest(LIST_NAME, boardId);
        listId = listUtils.getListId(createdListResponse);
    }

    @AfterMethod
    public void tearDown() {
        boardUtils.deleteBoardRequest(boardUtils.getBoardId(createdBoardResponse));
    }

    @Test(description = "Create a list")
    public void createList() {
        createdListResponse.then()
                .statusCode(HttpStatus.SC_OK);

        Response getListResponse = listUtils.getListRequest(listId);
        listUtils.checkGetBody(getListResponse, listId, LIST_NAME)
                .and()
                .body("idBoard", is(boardId));
    }

    @Test(description = "Update list name")
    public void updateList() {
        listUtils.updateListNameRequest(listId, NEW_LIST_NAME)
                .then()
                .statusCode(HttpStatus.SC_OK);

        Response getListResponse = listUtils.getListRequest(listId);
        listUtils.checkGetBody(getListResponse, listId, NEW_LIST_NAME);
    }

    @Test(description = "Get a list by id")
    public void getList() {
        Response getListResponse = listUtils.getListRequest(listId);
        listUtils.checkGetBody(getListResponse, listId, LIST_NAME)
                .and()
                .body("idBoard", is(boardId))
                .and()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(description = "Archive list")
    public void archiveList() {
        listUtils.archiveListRequest(listId).then()
                .statusCode(HttpStatus.SC_OK);

        Response getListResponse = listUtils.getListRequest(listId);
        listUtils.checkGetBody(getListResponse, listId, LIST_NAME)
                .and()
                .body("closed", is(true));
    }
}
