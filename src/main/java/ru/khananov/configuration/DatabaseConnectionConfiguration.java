package ru.khananov.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionConfiguration.class);

    public static Connection getConnection(String configFilePath) {
        AppConfiguration.loadConfig(configFilePath);
        String dbUrl = AppConfiguration.getProperty("db.url");
        String dbUsername = AppConfiguration.getProperty("db.username");
        String dbPassword = AppConfiguration.getProperty("db.password");

        try {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            logger.error("Connection error. Url, username or password are incorrect: " + e.getMessage(), e);
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Closing connection error: " + e.getMessage(), e);
            }
        }
    }
}