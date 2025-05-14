package com.bsujava.servlet.servlet;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.service.UrlShortenerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/urls/delete/*")
public class DeleteUrlServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUrlServlet.class);
    private final UrlShortenerService urlShortenerService;

    public DeleteUrlServlet() {
        this.urlShortenerService = new UrlShortenerService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Short code is required");
            return;
        }

        String shortCode = pathInfo.substring(1); // Remove the leading slash

        try {
            boolean deleted = urlShortenerService.deleteUrl(shortCode, user.getId());
            if (deleted) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "URL not found or not owned by user");
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting URL", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting URL");
        }
    }
} 
