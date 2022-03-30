package com.epam.tc.hw3.assertions;

import static org.testng.Assert.assertEquals;

import com.epam.tc.hw3.dto.ListDTO;

public class ListAssertions {

    public ListAssertions verifyName(ListDTO list, String expectedName) {
        assertEquals(list.getName(), expectedName);
        return this;
    }

    public ListAssertions verifyId(ListDTO list, String expectedId) {
        assertEquals(list.getId(), expectedId);
        return this;
    }

    public ListAssertions verifyBoardId(ListDTO list, String expectedId) {
        assertEquals(list.getIdBoard(), expectedId);
        return this;
    }

}
