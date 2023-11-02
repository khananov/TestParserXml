package ru.khananov.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class AppConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AppConfiguration.class);
    private static final Properties properties = new Properties();

    public static void loadConfig(String configFilePath) {
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(configFilePath));
        } catch (IOException e) {
            logger.info("Configuration file: " + configFilePath + " not found.\n" + e.getMessage(), e);
            logger.error(String.valueOf(e));
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}