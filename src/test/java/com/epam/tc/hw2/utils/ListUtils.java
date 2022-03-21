package com.epam.tc.hw2.utils;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class ListUtils extends AbstractUtils {

    private final String LISTS_END_POINT = "/1/lists/";
    private final String USER_LISTS_END_POINT = "/1/lists/%s";
    private final String LISTS_ARCHIVE_END_POINT = "/1/lists/%s/closed";

    public ListUtils() {
        super();
    }

    public Response createListRequest(String listName, String idBoard) {
        return given(requestSpecification)
                .queryParam("name", listName)
                .queryParam("idBoard", idBoard)
                .post(LISTS_END_POINT);
    }

    public String getListId(Response createdListResponse) {
        return createdListResponse.then().extract().path("id");
    }

    public Response getListRequest(String listId) {
        return given(requestSpecification)
                .get(String.format(USER_LISTS_END_POINT, listId));
    }

    public Response updateListNameRequest(String listId, String newListName) {
        return given(requestSpecification)
                .queryParam("name", newListName)
                .put(String.format(USER_LISTS_END_POINT, listId));
    }

    public Response archiveListRequest(String listId) {
        return given(requestSpecification)
                .queryParam("value", "true")
                .put(String.format(LISTS_ARCHIVE_END_POINT, listId));
    }
}
