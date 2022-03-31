package com.epam.tc.hw3.service;

import static com.epam.tc.hw3.utils.URI.BOARDS_END_POINT;

import com.epam.tc.hw3.dto.BoardDTO;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

public class BoardSteps extends CommonService {

    public BoardDTO createBoard(String name) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        Response response = makeRequest(Method.POST, BOARDS_END_POINT, params)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .extract()
                    .response();
        return fromResponseToDTO(response, BoardDTO.class);
    }

    public BoardDTO getBoardById(String id) {
        Response response = makeRequest(Method.GET, BOARDS_END_POINT + id);
        return fromResponseToDTO(response, BoardDTO.class);
    }

    public Response getBoardResponse(String id) {
        return makeRequest(Method.GET, BOARDS_END_POINT + id);
    }

    public Response deleteBoard(String id) {
        return makeRequest(Method.DELETE, BOARDS_END_POINT + id);
    }
}
