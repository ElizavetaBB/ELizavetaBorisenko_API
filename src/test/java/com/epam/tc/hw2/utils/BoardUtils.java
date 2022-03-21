package com.epam.tc.hw2.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class BoardUtils {

    private final String DOMAIN = "https://api.trello.com/";
    private final String BOARDS_END_POINT = "/1/boards/";
    private final String BOARDS_DELETE_END_POINT = "/1/boards/%s";

    private RequestSpecification REQUEST_SPECIFICATION;

    public BoardUtils() {
        PropertyReader propertyReader = new PropertyReader();
        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .setBaseUri(DOMAIN)
                .addQueryParam("key", propertyReader.getProperty("key"))
                .addQueryParam("token", propertyReader.getProperty("token"))
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public Response createBoard(String boardName) {
        return given(REQUEST_SPECIFICATION)
                .queryParam("name", boardName)
                .header("Content-type", "application/json")
                .post(BOARDS_END_POINT);
    }

    public Response deleteBoard(String boardId) {
        return given(REQUEST_SPECIFICATION)
                .header("Content-type", "application/json")
                .delete(String.format(BOARDS_DELETE_END_POINT, boardId));
    }

    public Response getBoardById(String boardId) {
        return given(REQUEST_SPECIFICATION)
                .get(String.format(BOARDS_DELETE_END_POINT, boardId));
    }

    public Response updateBoardById(String boardId, String description) {
        return given(REQUEST_SPECIFICATION)
                .queryParam("desc", description)
                .put(String.format(BOARDS_DELETE_END_POINT, boardId));
    }

    public String getBoardId(Response createdBoardResponse) {
        return createdBoardResponse.then().extract().path("id");
    }
}
