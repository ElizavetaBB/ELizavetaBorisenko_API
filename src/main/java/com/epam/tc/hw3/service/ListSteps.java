package com.epam.tc.hw3.service;

import com.epam.tc.hw3.dto.BoardDTO;
import com.epam.tc.hw3.dto.ListDTO;
import com.epam.tc.hw3.utils.URI;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ListSteps extends CommonService {

    public ListDTO createList(String name, BoardDTO board) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idBoard", board.getId());
        Response response = makeRequestWithParams(Request.POST, URI.LISTS_END_POINT, params)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().response();
        return fromResponseToDTO(response, ListDTO.class);
    }

    public ListDTO getListById(String id) {
        Response response = makeRequest(Request.GET_OK, String.format(URI.USER_LISTS_END_POINT, id));
        return fromResponseToDTO(response, ListDTO.class);
    }

    public Response getListResponse(String id) {
        return makeRequest(Request.GET, String.format(URI.USER_LISTS_END_POINT, id));
    }

}
