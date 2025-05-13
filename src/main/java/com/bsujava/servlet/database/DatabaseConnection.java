package com.bsujava.servlet.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties prop = new Properties();
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.fatal("Config file not found");
                throw new RuntimeException("DB configuration file not found");
            }

            prop.load(input);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException ex) {
            logger.fatal("Initialization error", ex);
            throw new ExceptionInInitializerError(ex);
        }

        URL = prop.getProperty("db.url");
        USER = prop.getProperty("db.user");
        PASSWORD = prop.getProperty("db.password");

        validateConfig();
    }

    public static String getUrl() {
        return URL;
    }

    public static String getUser() {
        return USER;
    }

    public static String getPassword() {
        return PASSWORD;
    }
    private static void validateConfig() {
        if (URL == null || USER == null || PASSWORD == null) {
            logger.fatal("Invalid database configuration");
            throw new RuntimeException("Missing DB configuration parameters");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}