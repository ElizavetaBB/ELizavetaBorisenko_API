package com.epam.tc.hw3.assertions;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;

public class ResponseAssertions {

    public void verifyStatusCode(Response response, int expectedCode) {
        assertThat(response.statusCode()).as("Response with expected status code : " + expectedCode)
                .isEqualTo(expectedCode);
    }
}
