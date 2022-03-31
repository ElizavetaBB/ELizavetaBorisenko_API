package com.epam.tc.hw3.service;

import static com.epam.tc.hw3.utils.URI.LISTS_END_POINT;

import com.epam.tc.hw3.dto.BoardDTO;
import com.epam.tc.hw3.dto.ListDTO;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

public class ListSteps extends CommonService {

    public ListDTO createList(String name, BoardDTO board) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idBoard", board.getId());
        Response response = makeRequest(Method.POST, LISTS_END_POINT, params)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .extract()
                    .response();
        return fromResponseToDTO(response, ListDTO.class);
    }

    public ListDTO getListById(String id) {
        Response response = makeRequest(Method.GET, LISTS_END_POINT + id)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .extract()
                    .response();
        return fromResponseToDTO(response, ListDTO.class);
    }

    public Response getListResponse(String id) {
        return makeRequest(Method.GET, LISTS_END_POINT + id);
    }

}
