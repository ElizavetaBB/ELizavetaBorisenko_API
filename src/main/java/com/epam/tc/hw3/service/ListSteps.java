package com.epam.tc.hw3.service;

import com.epam.tc.hw3.dto.BoardDTO;
import com.epam.tc.hw3.dto.ListDTO;
import com.epam.tc.hw3.utils.URI;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ListSteps extends CommonService {

    public ListDTO createList(String name, BoardDTO board) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("idBoard", board.getId());
        Response response = post(URI.LISTS_END_POINT, params)
                .then().statusCode(HttpStatus.SC_OK)
                .extract().response();
        return new Gson().fromJson(response.getBody().asString(), ListDTO.class);
    }

    public ListDTO getListById(String id) {
        return new Gson().fromJson(getWithOKCode(String.format(URI.USER_LISTS_END_POINT, id)).getBody().asString(), ListDTO.class);
    }

    public Response getListResponse(String id) {
        return get(String.format(URI.USER_LISTS_END_POINT, id));
    }

}
