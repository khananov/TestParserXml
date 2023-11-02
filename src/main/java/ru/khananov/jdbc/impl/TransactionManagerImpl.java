package ru.khananov.jdbc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.khananov.jdbc.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManagerImpl implements TransactionManager {
    private static final Logger logger = LoggerFactory.getLogger(TransactionManagerImpl.class);
    private final Connection connection;

    public TransactionManagerImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    @Override
    public void rollback() {
        try {
            if (!connection.getAutoCommit()) {
                connection.rollback();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.info("Rollback error: " + e.getMessage());
            logger.error(String.valueOf(e));
        }
    }
}