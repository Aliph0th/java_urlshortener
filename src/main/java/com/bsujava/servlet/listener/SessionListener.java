package com.bsujava.servlet.listener;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionListener implements HttpSessionListener {

    private static final Logger logger = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("🆕 Session created: {}", se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("💨 Session destroyed: {}", se.getSession().getId());
    }
}
