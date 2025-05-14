package com.bsujava.servlet.servlet;

import com.bsujava.servlet.model.ShortUrl;
import com.bsujava.servlet.service.UrlShortenerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/s/*")
public class RedirectServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(RedirectServlet.class);
    private final UrlShortenerService urlShortenerService;

    public RedirectServlet() {
        this.urlShortenerService = new UrlShortenerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String shortCode = pathInfo.substring(1); // Remove leading slash
        Optional<ShortUrl> shortUrl = urlShortenerService.getOriginalUrl(shortCode);

        if (shortUrl.isPresent()) {
            response.sendRedirect(shortUrl.get().getOriginalUrl());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Short URL not found");
        }
    }
}
