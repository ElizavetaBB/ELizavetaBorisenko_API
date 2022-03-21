package com.epam.tc.hw2.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class CardUtils {

    private final String DOMAIN = "https://api.trello.com/";
    private final String CARDS_END_POINT = "/1/cards/";

    private RequestSpecification REQUEST_SPECIFICATION;

    public CardUtils() {
        PropertyReader propertyReader = new PropertyReader();
        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .setBaseUri(DOMAIN)
                .addQueryParam("key", propertyReader.getProperty("key"))
                .addQueryParam("token", propertyReader.getProperty("token"))
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public Response createCard(String cardName, String listId) {
        return given(REQUEST_SPECIFICATION)
                .queryParam("name", cardName)
                .queryParam("idList", listId)
                .header("Content-type", "application/json")
                .post(CARDS_END_POINT);
    }
}
