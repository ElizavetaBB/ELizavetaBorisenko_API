package com.epam.tc.hw2.low_level;

import com.epam.tc.hw2.utils.BoardUtils;
import com.epam.tc.hw2.utils.ListUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;

public class ListTests {

    private final String BOARD_NAME = "board";
    private final String LIST_NAME = "list name";
    private final String NEW_LIST_NAME = "new list name";

    private BoardUtils boardUtils;
    private ListUtils listUtils;
    private Response createdListResponse;
    private Response createdBoardResponse;

    @BeforeMethod
    public void setUp() {
        boardUtils = new BoardUtils();
        createdBoardResponse = boardUtils.createBoard(BOARD_NAME);
        listUtils = new ListUtils();
        createdListResponse = listUtils.createList(LIST_NAME, boardUtils.getBoardId(createdBoardResponse));
    }

    @Test(description = "create a board with name and check status")
    public void createList() {
        createdListResponse.then()
                .statusCode(HttpStatus.SC_OK);
        // check that created
        listUtils.getList(listUtils.getListId(createdListResponse)).then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", is(LIST_NAME))
                .body("idBoard", is(boardUtils.getBoardId(createdBoardResponse)));
    }

    @Test
    public void updateList() {
        listUtils.updateListName(listUtils.getListId(createdListResponse), NEW_LIST_NAME)
                .then()
                .statusCode(HttpStatus.SC_OK);
        // check that updated
        listUtils.getList(listUtils.getListId(createdListResponse)).then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", is(NEW_LIST_NAME))
                .body("id", is(listUtils.getListId(createdListResponse)));
    }
}
