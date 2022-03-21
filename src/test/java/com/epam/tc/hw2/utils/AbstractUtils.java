package com.epam.tc.hw2.utils;

import static com.epam.tc.hw2.data.CommonData.DOMAIN_URL;
import static org.hamcrest.Matchers.is;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class AbstractUtils {

    protected RequestSpecification requestSpecification;

    protected AbstractUtils() {
        PropertyReader propertyReader = new PropertyReader();
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(DOMAIN_URL)
                .addQueryParam("key", propertyReader.getProperty("key"))
                .addQueryParam("token", propertyReader.getProperty("token"))
                .addHeader("Content-type", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public ValidatableResponse checkGetBody(Response getResponse, String id, String name) {
        return getResponse.then()
                .body("id", is(id))
                .body("name", is(name));
    }
}
