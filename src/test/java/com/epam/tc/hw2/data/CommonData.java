package com.epam.tc.hw2.data;

import com.epam.tc.hw2.utils.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.util.Properties;

public class CommonData {

    public static final String DOMAIN_URL = "https://api.trello.com/";
    public static final String BOARD_NAME = "board new new";
    public static final String LIST_NAME = "list";
    public static final Properties propertyReader = PropertyReader.createPropertyReader();
    public static final RequestSpecification REQUEST_SPECIFICATION =  new RequestSpecBuilder()
            .setBaseUri(DOMAIN_URL)
            .addQueryParam("key", propertyReader.getProperty("key"))
            .addQueryParam("token", propertyReader.getProperty("token"))
            .addHeader("Content-type", "application/json")
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();
}
