package com.epam.tc.hw3.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import com.epam.tc.hw3.dto.ListDTO;

public class ListAssertions {

    public ListAssertions verifyName(ListDTO list, String expectedName) {
        assertThat(list.getName()).as("List has an appropriate name : " + expectedName)
                .isEqualTo(expectedName);
        return this;
    }

    public ListAssertions verifyId(ListDTO list, String expectedId) {
        assertThat(list.getId()).as("List has an appropriate id : " + expectedId).isEqualTo(expectedId);
        return this;
    }

    public ListAssertions verifyBoardId(ListDTO list, String expectedId) {
        assertThat(list.getIdBoard()).as("List has an appropriate boardId : " + expectedId).isEqualTo(expectedId);
        return this;
    }

}
