package com.epam.tc.hw3.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import com.epam.tc.hw3.dto.BoardDTO;
import io.restassured.response.Response;

public class BoardAssertions {

    public BoardAssertions verifyName(BoardDTO board, String expectedName) {
        assertThat(board.getName()).as("Board has an appropriate name : " + expectedName)
                .isEqualTo(expectedName);
        return this;
    }

    public BoardAssertions verifyId(BoardDTO board, String expectedId) {
        assertThat(board.getId()).as("Board has an appropriate id : " + expectedId).isEqualTo(expectedId);
        return this;
    }

    public BoardAssertions verifyDeletedBoard(Response response) {
        String value = response.then().extract().path("_value");
        assertThat(value).as("Response _value is null").isNull();
        return this;
    }

}
