package com.webjavabsu2ndcourse4ndterm.shortener.servlets;

import com.webjavabsu2ndcourse4ndterm.services.DatabaseService;
import com.webjavabsu2ndcourse4ndterm.shortener.models.Url;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/api/urls/*")
public class UrlServlet extends HttpServlet {
    private final DatabaseService databaseService = new DatabaseService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        JSONObject jsonInput = new JSONObject(sb.toString());
        String originalUrl = jsonInput.getString("original_url");
        String shortUrl = UUID.randomUUID().toString();
        LocalDateTime expiresAt = jsonInput.has("expires_at") ? LocalDateTime.parse(jsonInput.getString("expires_at")) : null;
        Long userId = jsonInput.has("user_id") ? jsonInput.getLong("user_id") : null;

        Url url = databaseService.insertUrl(originalUrl, shortUrl, expiresAt, userId);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("url", url);
        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String shortUrl = request.getParameter("short_id");
        if (shortUrl != null) {
            String originalUrl = databaseService.getOriginalUrl(shortUrl);
            if (originalUrl != null) {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("original_url", originalUrl);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"URL not found\"}");
            }
        } else {
            List<Url> urls = databaseService.getAllUrls();
            JSONArray jsonArray = new JSONArray(urls);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonArray.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No ID specified");
            return;
        }

        String idString = pathInfo.substring(1);
        Long id = Long.parseLong(idString);
        databaseService.deleteUrl(id);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}
