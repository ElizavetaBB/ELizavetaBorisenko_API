package com.epam.tc.hw3.assertions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import com.epam.tc.hw3.dto.BoardDTO;
import io.restassured.response.Response;

public class BoardAssertions {

    public BoardAssertions verifyName(BoardDTO board, String expectedName) {
        assertEquals(board.getName(), expectedName);
        return this;
    }

    public BoardAssertions verifyId(BoardDTO board, String expectedId) {
        assertEquals(board.getId(), expectedId);
        return this;
    }

    public BoardAssertions verifyDeletedBoard(Response response) {
        String value = response.then().extract().path("_value");
        assertNull(value);
        return this;
    }

}
