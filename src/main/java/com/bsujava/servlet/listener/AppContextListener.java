package com.bsujava.servlet.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(AppContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("🔧 Application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("🛑 Application stopped");
    }
}
