package com.bsujava.servlet.dao.impl;

import com.bsujava.servlet.pool.ConnectionPool;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDao {
    protected final ConnectionPool connectionPool;

    protected BaseDao() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    protected Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }
}