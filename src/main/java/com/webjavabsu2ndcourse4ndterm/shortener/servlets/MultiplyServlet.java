package com.webjavabsu2ndcourse4ndterm.shortener.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/multiply")
public class MultiplyServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MultiplyServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws JSONException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonString = sb.toString();
        logger.info("Received JSON: {}", jsonString);

        try {
            JSONObject jsonInput = new JSONObject(jsonString);
            float number = jsonInput.getFloat("number");

            float result = number * 2;
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("result", result);

            response.getWriter().write(jsonResponse.toString());
        } catch (JSONException e) {
            logger.error("Invalid number format: {}", jsonString);
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("error", "Invalid number format");
            response.getWriter().write(jsonResponse.toString());
        }
    }
}
