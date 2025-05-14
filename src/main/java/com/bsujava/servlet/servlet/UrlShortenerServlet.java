package com.bsujava.servlet.servlet;

import com.bsujava.servlet.entity.User;
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
import java.util.List;

@WebServlet({"/shorten", "/urls"})
public class UrlShortenerServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(UrlShortenerServlet.class);
    private final UrlShortenerService urlShortenerService;

    public UrlShortenerServlet() {
        this.urlShortenerService = new UrlShortenerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        List<ShortUrl> userUrls = urlShortenerService.getUserUrls(user.getId());
        request.setAttribute("urls", userUrls);
        request.getRequestDispatcher("/urls/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String originalUrl = request.getParameter("url");
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            request.setAttribute("error", "URL cannot be empty");
            request.getRequestDispatcher("/urls/create.jsp").forward(request, response);
            return;
        }

        try {
            ShortUrl shortUrl = urlShortenerService.createShortUrl(originalUrl, user.getId());
            request.setAttribute("shortUrl", shortUrl);
            request.setAttribute("baseUrl", getBaseUrl(request));
            request.getRequestDispatcher("/urls/success.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.error("Error creating short URL", e);
            request.setAttribute("error", "Error creating short URL");
            request.getRequestDispatcher("/urls/create.jsp").forward(request, response);
        }
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath);
        return url.toString();
    }
}
