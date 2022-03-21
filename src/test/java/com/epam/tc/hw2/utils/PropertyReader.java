package com.epam.tc.hw2.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    private Properties property;

    public PropertyReader() {
        try (FileReader fileReader = new FileReader("src/test/resources/homework_2/test.properties")) {
            property = new Properties();
            property.load(fileReader);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getProperty(final String key) {
        return property.getProperty(key);
    }
}
