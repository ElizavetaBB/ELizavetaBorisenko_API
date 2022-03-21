package com.epam.tc.hw2.low_level;

import static com.epam.tc.hw2.data.CommonData.BOARD_NAME;
import static com.epam.tc.hw2.data.CommonData.LIST_NAME;
import static org.hamcrest.Matchers.*;

import com.epam.tc.hw2.utils.BoardUtils;
import com.epam.tc.hw2.utils.CardUtils;
import com.epam.tc.hw2.utils.ListUtils;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CardTests {

    private final String CARD_NAME = "card name";
    private final String CARD_DESC = "card description";

    private BoardUtils boardUtils;
    private ListUtils listUtils;
    private CardUtils cardUtils;

    private Response createdListResponse;
    private Response createdCardResponse;
    private String createdBoardId;
    private String createdListId;
    private String createdCardId;

    @BeforeMethod
    public void setUp() {
        boardUtils = new BoardUtils();
        Response createdBoardResponse = boardUtils.createBoardRequest(BOARD_NAME);
        createdBoardId = boardUtils.getBoardId(createdBoardResponse);
        listUtils = new ListUtils();
        createdListResponse = listUtils.createListRequest(LIST_NAME, createdBoardId);
        createdListId = listUtils.getListId(createdListResponse);
        cardUtils = new CardUtils();
        createdCardResponse = cardUtils.createCardRequest(CARD_NAME, createdListId);
        createdCardId = cardUtils.getCardId(createdCardResponse);
    }

    @AfterMethod
    public void tearDown() {
        boardUtils.deleteBoardRequest(createdBoardId);
    }

    @Test(description = "Create a card")
    public void createCard() {
        createdCardResponse.then()
                .statusCode(HttpStatus.SC_OK);

        Response getCardResponse = cardUtils.getCardRequest(createdCardId);
        cardUtils.checkGetBody(getCardResponse, createdCardId, CARD_NAME)
                .and()
                .body("idList", is(createdListId));
    }

    @Test(description = "Get a card by id")
    public void getCard() {
        Response getCardResponse = cardUtils.getCardRequest(createdCardId);
        cardUtils.checkGetBody(getCardResponse, createdCardId, CARD_NAME)
                .and()
                .body("idList", is(createdListId))
                .and()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(description = "Update description of the card")
    public void updateCard() {
        cardUtils.updateCardDescRequest(createdCardId, CARD_DESC);

        Response getCardResponse = cardUtils.getCardRequest(createdCardId);
        cardUtils.checkGetBody(getCardResponse, createdCardId, CARD_NAME)
                .and()
                .body("desc", is(CARD_DESC));
    }

    @Test(description = "Delete a card")
    public void deleteCard() {
        cardUtils.deleteCardRequest(createdCardId).then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("_value", nullValue());

        cardUtils.getCardRequest(createdCardId).then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
