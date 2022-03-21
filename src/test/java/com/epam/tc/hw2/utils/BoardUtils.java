package com.epam.tc.hw2.utils;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class BoardUtils extends AbstractUtils {

    private final String BOARDS_END_POINT = "/1/boards/";
    private final String USER_BOARDS_END_POINT = "/1/boards/%s";

    public BoardUtils() {
        super();
    }

    public Response createBoardRequest(String boardName) {
        return given(requestSpecification)
                .queryParam("name", boardName)
                .post(BOARDS_END_POINT);
    }

    public Response deleteBoardRequest(String boardId) {
        return given(requestSpecification)
                .delete(String.format(USER_BOARDS_END_POINT, boardId));
    }

    public Response getBoardByIdRequest(String boardId) {
        return given(requestSpecification)
                .get(String.format(USER_BOARDS_END_POINT, boardId));
    }

    public Response updateBoardByIdRequest(String boardId, String description) {
        return given(requestSpecification)
                .queryParam("desc", description)
                .put(String.format(USER_BOARDS_END_POINT, boardId));
    }

    public String getBoardId(Response createdBoardResponse) {
        return createdBoardResponse.then().extract().path("id");
    }

}
