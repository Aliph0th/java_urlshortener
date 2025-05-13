package com.bsujava.servlet.listener;


import com.bsujava.servlet.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ConnectionPoolListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ConnectionPoolListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("⚙️ Initializing ConnectionPool");
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("♻️ Shutting down ConnectionPool");
        ConnectionPool.getInstance().shutdown();
    }
}
