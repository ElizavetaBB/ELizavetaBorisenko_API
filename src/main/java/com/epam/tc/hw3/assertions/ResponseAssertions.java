package com.epam.tc.hw3.assertions;

import static org.testng.Assert.assertEquals;

import io.restassured.response.Response;

public class ResponseAssertions {

    public void verifyStatusCode(Response response, int expectedCode) {
        assertEquals(response.statusCode(), expectedCode);
    }
}
