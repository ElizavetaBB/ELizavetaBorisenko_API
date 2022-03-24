package com.epam.tc.hw2.data;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public static Properties createPropertyReader() {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader("src/test/resources/homework_2/test.properties")) {
            properties.load(fileReader);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return properties;
    }
}
