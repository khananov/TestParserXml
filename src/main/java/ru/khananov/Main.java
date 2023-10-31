package ru.khananov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Properties properties = loadProperties("configuration.properties");
        String dbUrl = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");
        logger.error(dbUrl + dbUsername + dbPassword);

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword) ) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Properties loadProperties(String propertiesName) {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(propertiesName));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return properties;
    }
}