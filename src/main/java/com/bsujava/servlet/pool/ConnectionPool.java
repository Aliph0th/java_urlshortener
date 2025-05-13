package com.bsujava.servlet.pool;

import com.bsujava.servlet.database.DatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);

    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private static final long TIMEOUT_MS = 5000;

    private final BlockingQueue<Connection> availableConnections = new LinkedBlockingQueue<>(MAX_POOL_SIZE);
    private final BlockingQueue<Connection> usedConnections = new LinkedBlockingQueue<>(MAX_POOL_SIZE);

    private ConnectionPool() {
        initializePool();
    }

    private static class SingletonHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void initializePool() {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            availableConnections.add(createProxyConnection());
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection connection = availableConnections.poll(TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (connection == null) {
                if (usedConnections.size() < MAX_POOL_SIZE) {
                    connection = createProxyConnection();
                } else {
                    throw new SQLException("Connection pool exhausted");
                }
            }
            usedConnections.put(connection);
            return connection;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Interrupted while waiting for connection", e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            usedConnections.remove(connection);
            availableConnections.offer(connection);
        }
    }

    private Connection createProxyConnection() {
        try {
            Connection realConnection = DriverManager.getConnection(
                    DatabaseConnection.getUrl(),
                    DatabaseConnection.getUser(),
                    DatabaseConnection.getPassword()
            );

            return (Connection) java.lang.reflect.Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if ("close".equals(method.getName())) {
                            releaseConnection((Connection) proxy);
                            return null;
                        }
                        return method.invoke(realConnection, args);
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create connection", e);
        }
    }

    public void shutdown() {
        closeConnections(availableConnections);
        closeConnections(usedConnections);
        availableConnections.clear();
        usedConnections.clear();
    }

    private void closeConnections(BlockingQueue<Connection> connections) {
        connections.forEach(conn -> {
            try {
                if (!conn.isClosed()) {
                    Connection realConn = conn.unwrap(Connection.class);
                    realConn.close();
                }
            } catch (SQLException e) {
                LOGGER.error("Failed to close connection", e);
            }
        });
    }
}