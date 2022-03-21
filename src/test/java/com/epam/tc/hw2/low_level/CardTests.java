package com.epam.tc.hw2.low_level;

import com.epam.tc.hw2.utils.BoardUtils;
import com.epam.tc.hw2.utils.CardUtils;
import com.epam.tc.hw2.utils.ListUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;

public class CardTests {

    private final String BOARD_NAME = "board";
    private final String LIST_NAME = "list name";
    private final String CARD_NAME = "card name";
    private Response createdCardResponse;
    private Response createdListResponse;

    private BoardUtils boardUtils;
    private ListUtils listUtils;
    private CardUtils cardUtils;

    @BeforeMethod
    public void setUp() {
        boardUtils = new BoardUtils();
        listUtils = new ListUtils();
        Response createdBoardResponse = boardUtils.createBoard(BOARD_NAME);
        createdListResponse = listUtils.createList(LIST_NAME, boardUtils.getBoardId(createdBoardResponse));
        cardUtils = new CardUtils();
        createdCardResponse = cardUtils.createCard(CARD_NAME, listUtils.getListId(createdListResponse));
    }

    @Test(description = "create a board with name and check status")
    public void createCard() {
        createdCardResponse.then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("name", is(CARD_NAME))
                .body("idList", is(listUtils.getListId(createdListResponse)));
    }
}
