package com.webjavabsu2ndcourse4ndterm.shortener.servlets;

import com.webjavabsu2ndcourse4ndterm.exceptions.HttpException;
import com.webjavabsu2ndcourse4ndterm.services.DatabaseService;
import com.webjavabsu2ndcourse4ndterm.services.UserService;
import com.webjavabsu2ndcourse4ndterm.shortener.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/users/*")
public class UserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            Long userId = Long.parseLong(pathInfo.substring(1));
            JSONObject jsonResponse = userService.getUser(userId);
            response.getWriter().write(jsonResponse.toString());
        } else {
            JSONArray jsonArray = userService.getUsers();
            response.getWriter().write(jsonArray.toString());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws HttpException, IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            throw new HttpException("No ID specified");
        }

        Long userId = Long.parseLong(pathInfo.substring(1));
        userService.deleteUser(userId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }
}