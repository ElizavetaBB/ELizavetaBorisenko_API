package com.epam.tc.hw3;

import com.epam.tc.hw3.assertions.CardAssertions;
import com.epam.tc.hw3.assertions.ResponseAssertions;
import com.epam.tc.hw3.data.BaseData;
import com.epam.tc.hw3.data.TrelloDataProvider;
import com.epam.tc.hw3.dto.BoardDTO;
import com.epam.tc.hw3.dto.CardDTO;
import com.epam.tc.hw3.dto.ListDTO;
import com.epam.tc.hw3.dto.StickerDTO;
import com.epam.tc.hw3.service.BoardSteps;
import com.epam.tc.hw3.service.CardSteps;
import com.epam.tc.hw3.service.ListSteps;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CardTests {

    private BoardSteps boardSteps;
    private CardSteps cardSteps;
    private CardAssertions cardAssertions;
    private BoardDTO board;
    private ListDTO list;

    @BeforeMethod
    public void setUp() {
        boardSteps = new BoardSteps();
        ListSteps listSteps = new ListSteps();
        board = boardSteps.createBoard(BaseData.BOARD_NAME);
        list = listSteps.createList(BaseData.LIST_NAME, board);
        cardSteps = new CardSteps();
        cardAssertions = new CardAssertions();
    }

    @AfterMethod
    public void tearDown() {
        boardSteps.deleteBoard(board.getId());
    }

    @Test(description = "Create a card and check it was created",
            dataProviderClass = TrelloDataProvider.class, dataProvider = "Card names")
    public void createCard(String name) {
        String cardId = cardSteps
                .createCard(name, list)
                .getId();

        CardDTO card = cardSteps.getCardById(cardId);
        cardAssertions
                .verifyId(card, cardId)
                .verifyName(card, name)
                .verifyListId(card, list.getId());
    }

    @Test(description = "Create stickers on a card and check they were created",
            dataProviderClass = TrelloDataProvider.class, dataProvider = "Stickers")
    public void createStickers(String cardName, StickerDTO... stickers) {
        String cardId = cardSteps
                .createCard(cardName, list)
                .getId();
        cardSteps.createStickers(cardId, stickers);

        CardDTO card = cardSteps.getCardById(cardId);
        cardAssertions.verifyStickers(card, stickers);
    }

    @Test(description = "Delete a card and check it was deleted",
            dataProviderClass = TrelloDataProvider.class, dataProvider = "Card names")
    public void deleteCard(String name) {
        String cardId = cardSteps
                .createCard(name, list)
                .getId();
        Response response = cardSteps.deleteCard(cardId);
        cardAssertions.verifyDeletedCard(response);

        Response getBoard = cardSteps.getCardResponse(cardId);
        new ResponseAssertions().verifyStatusCode(getBoard, HttpStatus.SC_NOT_FOUND);
    }

}
