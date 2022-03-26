package com.epam.tc.hw3.service;

import static io.restassured.RestAssured.given;

import com.epam.tc.hw3.utils.PropertyReader;
import com.epam.tc.hw3.utils.URI;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import java.util.Map;
import java.util.Properties;

public class CommonService {

    protected RequestSpecification REQUEST_SPECIFICATION;

    public CommonService() {
        Properties propertyReader = PropertyReader.createPropertyReader();
        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .setBaseUri(URI.DOMAIN_URI)
                .addQueryParam("key", propertyReader.getProperty("key"))
                .addQueryParam("token", propertyReader.getProperty("token"))
                .addHeader("Content-type", "application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public Response getWithOKCode(String uri) {
        return given(REQUEST_SPECIFICATION).get(uri)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .extract().response();
    }

    public Response get(String uri) {
        return given(REQUEST_SPECIFICATION).get(uri)
                .then()
                .extract().response();
    }

    public Response post(String uri, Map<String, String> params) {
        return given(REQUEST_SPECIFICATION).queryParams(params).post(uri);
    }

    public Response delete(String uri) {
        return given(REQUEST_SPECIFICATION).delete(uri);
    }
}
