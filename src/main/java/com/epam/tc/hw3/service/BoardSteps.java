package com.epam.tc.hw3.service;

import com.epam.tc.hw3.dto.BoardDTO;
import com.epam.tc.hw3.utils.URI;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

public class BoardSteps extends CommonService {

    public BoardDTO createBoard(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        Response response = makeRequestWithParams(Request.POST, URI.BOARDS_END_POINT, params)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().response();
        return new Gson().fromJson(response.getBody().asString(), BoardDTO.class);
    }

    public BoardDTO getBoardById(String id) {
        Response response = makeRequest(Request.GET_OK, String.format(URI.USER_BOARD_END_POINT, id));
        return fromResponseToDTO(response, BoardDTO.class);
    }

    public Response getBoardResponse(String id) {
        return makeRequest(Request.GET, String.format(URI.USER_BOARD_END_POINT, id));
    }

    public Response deleteBoard(String id) {
        return makeRequest(Request.DELETE, String.format(URI.USER_BOARD_END_POINT, id));
    }
}