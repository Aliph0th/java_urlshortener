package com.bsujava.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter("/*")
public class ActivityLoggingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(ActivityLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        String path = httpRequest.getRequestURI();
        String user = "anonymous";

        if (session != null && session.getAttribute("user") != null) {
            user = session.getAttribute("user").toString(); // предполагаем toString() даст что-то информативное
        }

        logger.info("👣 [{}] accessed {}", user, path);

        chain.doFilter(request, response);
    }
}
