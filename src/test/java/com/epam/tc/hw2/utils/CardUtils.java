package com.epam.tc.hw2.utils;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class CardUtils extends AbstractUtils {

    private final String CARDS_END_POINT = "/1/cards/";
    private final String USER_CARDS_END_POINT = "/1/cards/%s";

    public CardUtils() {
        super();
    }

    public Response createCardRequest(String cardName, String listId) {
        return given(requestSpecification)
                .queryParam("name", cardName)
                .queryParam("idList", listId)
                .post(CARDS_END_POINT);
    }

    public Response getCardRequest(String cardId) {
        return given(requestSpecification)
                .get(String.format(USER_CARDS_END_POINT, cardId));
    }

    public String getCardId(Response createdCardResponse) {
        return createdCardResponse.then().extract().path("id");
    }

    public Response updateCardDescRequest(String cardId, String desc) {
        return given(requestSpecification)
                    .queryParam("desc", desc)
                    .put(String.format(USER_CARDS_END_POINT, cardId));
    }

    public Response deleteCardRequest(String cardId) {
        return given(requestSpecification)
                .delete(String.format(USER_CARDS_END_POINT, cardId));
    }
}
