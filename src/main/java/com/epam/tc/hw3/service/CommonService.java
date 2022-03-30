package com.epam.tc.hw3.service;

import static io.restassured.RestAssured.given;

import com.epam.tc.hw3.utils.PropertyReader;
import com.epam.tc.hw3.utils.URI;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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

    public Response makeRequest(Method method, String uri) {
        return given(REQUEST_SPECIFICATION).request(method, uri);
    }

    public Response makeRequest(Method method, String uri, Map<String, String> params) {
         return given(REQUEST_SPECIFICATION)
                    .queryParams(params)
                    .request(method, uri);
    }

    public <T> T fromResponseToDTO(Response response, Class<T> dtoClass) {
        return new Gson().fromJson(response.getBody().asString(), dtoClass);
    }
}
