package com.epam.tc.hw2.low_level;

import static com.epam.tc.hw2.data.CommonData.*;
import static com.epam.tc.hw2.data.CommonData.REQUEST_SPECIFICATION;
import static com.epam.tc.hw2.data.TrelloURL.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CardTests {

    private final String CARD_NAME = "card name";
    private final String CARD_DESC = "card description";
    private final String CHECKLIST_NAME = "checklist";
    private final String[] CHECKLIST_OPTIONS = new String[]{"option 1", "new option", "one more option"};

    private Response createdCardResponse;
    private String boardId;
    private String listId;
    private String cardId;

    @BeforeMethod
    public void setUp() {
        Response createdBoardResponse = given(REQUEST_SPECIFICATION)
                .queryParam("name", BOARD_NAME)
                .post(BOARDS_END_POINT);
        boardId = createdBoardResponse.then().extract().path("id");
        Response createdListResponse = given(REQUEST_SPECIFICATION)
                .queryParams("idBoard", boardId,
                        "name", LIST_NAME)
                .post(LISTS_END_POINT);
        listId = createdListResponse.then().extract().path("id");
        createdCardResponse = given(REQUEST_SPECIFICATION)
                .queryParams("name", CARD_NAME,
                        "idList", listId)
                .post(CARDS_END_POINT);
        cardId = createdCardResponse.then().extract().path("id");
    }

    @AfterMethod
    public void tearDown() {
        given(REQUEST_SPECIFICATION).delete(USER_BOARDS_END_POINT, boardId);
    }

    @Test(description = "Create a card")
    public void createCard() {
        createdCardResponse
                .then()
                    .statusCode(HttpStatus.SC_OK);

        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_CARDS_END_POINT, cardId)
                .then()
                    .body("idList", is(listId),
                        "id", is(cardId),
                        "name", is(CARD_NAME));
    }

    @Test(description = "Create a checklist on a card")
    public void createChecklist() {
        // create a checklist
        String checklistId = given(REQUEST_SPECIFICATION)
                .when()
                    .queryParams("name", CHECKLIST_NAME,
                        "idCard", cardId)
                    .post(CHECKLISTS_END_POINT)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .extract().path("id");

        // create checkItems on the list
        for (String item : CHECKLIST_OPTIONS) {
            given(REQUEST_SPECIFICATION)
                    .when()
                        .queryParam("name", item)
                        .post(USER_CHECKITEMS_END_POINT, checklistId)
                    .then()
                        .statusCode(HttpStatus.SC_OK);
        }

        // check created checklist and its items
        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_CHECKLISTS_END_POINT, checklistId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .body("id", is(checklistId),
                            "idCard", is(cardId),
                            "name", is(CHECKLIST_NAME),
                            "checkItems.name", containsInAnyOrder(CHECKLIST_OPTIONS));
    }

    @Test(description = "Get a card by id")
    public void getCard() {
        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_CARDS_END_POINT, cardId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .body("idList", is(listId),
                        "id", is(cardId),
                        "name", is(CARD_NAME));
    }

    @Test(description = "Update description of the card")
    public void updateCard() {
        given(REQUEST_SPECIFICATION)
                .when()
                    .queryParam("desc", CARD_DESC)
                    .put(USER_CARDS_END_POINT, cardId)
                .then()
                    .statusCode(HttpStatus.SC_OK);

        given(REQUEST_SPECIFICATION)
                .when()
                    .get(USER_CARDS_END_POINT, cardId)
                .then()
                    .body("id", equalTo(cardId),
                        "desc", equalTo(CARD_DESC));
    }

    @Test(description = "Delete a card")
    public void deleteCard() {
        given(REQUEST_SPECIFICATION)
                .when()
                        .delete(USER_CARDS_END_POINT, cardId)
                .then()
                        .statusCode(HttpStatus.SC_OK)
                        .body("_value", nullValue());

        given(REQUEST_SPECIFICATION)
                .when()
                        .get(USER_CARDS_END_POINT, cardId)
                .then()
                        .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
