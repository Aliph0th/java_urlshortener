package com.bsujava.servlet.servlet;

import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.ActivationCodeService;
import com.bsujava.servlet.service.impl.ActivationCodeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/confirm")
public class ConfirmServlet extends HttpServlet {
    private final ActivationCodeService activationCodeService = new ActivationCodeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        try {
            if (activationCodeService.verifyCode(token)) {
                response.sendRedirect(request.getContextPath() + "/auth/login.jsp?message=Account+activated");
            } else {
                request.setAttribute("error", "Invalid or expired token");
                request.getRequestDispatcher("/auth/error.jsp").forward(request, response);
            }
        } catch (DatabaseException e) {
            request.setAttribute("error", "Activation failed: " + e.getMessage());
            request.getRequestDispatcher("/auth/error.jsp").forward(request, response);
        }
    }
}
