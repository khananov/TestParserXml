package ru.khananov.jdbc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.khananov.configuration.AppConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    public static Connection getConnection(String configFilePath) {
        AppConfiguration.loadConfig(configFilePath);
        String dbUrl = AppConfiguration.getProperty("db.url");
        String dbUsername = AppConfiguration.getProperty("db.username");
        String dbPassword = AppConfiguration.getProperty("db.password");

        try {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            logger.info("Connection error. Url, username or password are incorrect: " + e.getMessage(), e);
            logger.error(String.valueOf(e));
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.info("Closing connection error: " + e.getMessage(), e);
                logger.error(String.valueOf(e));
            }
        }
    }
}