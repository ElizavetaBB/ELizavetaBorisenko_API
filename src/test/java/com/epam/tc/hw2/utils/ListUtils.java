package com.epam.tc.hw2.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ListUtils {

    private final String DOMAIN = "https://api.trello.com/";
    private final String LISTS_END_POINT = "/1/lists/";
    private final String LIST_GET_END_POINT = "/1/lists/%s";

    private RequestSpecification REQUEST_SPECIFICATION;

    public ListUtils() {
        PropertyReader propertyReader = new PropertyReader();
        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .setBaseUri(DOMAIN)
                .addQueryParam("key", propertyReader.getProperty("key"))
                .addQueryParam("token", propertyReader.getProperty("token"))
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public Response createList(String listName, String idBoard) {
        return given(REQUEST_SPECIFICATION)
                .queryParam("name", listName)
                .queryParam("idBoard", idBoard)
                .header("Content-type", "application/json")
                .post(LISTS_END_POINT);
    }

    public String getListId(Response createdListResponse) {
        return createdListResponse.then().extract().path("id");
    }

    public Response getList(String listId) {
        return given(REQUEST_SPECIFICATION)
                .header("Content-type", "application/json")
                .get(String.format(LIST_GET_END_POINT, listId));
    }

    public Response updateListName(String listId, String newListName) {
        return given(REQUEST_SPECIFICATION)
                .queryParam("name", newListName)
                .header("Content-type", "application/json")
                .put(String.format(LIST_GET_END_POINT, listId));
    }
}
