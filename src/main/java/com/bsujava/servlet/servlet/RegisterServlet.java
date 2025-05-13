package com.bsujava.servlet.servlet;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.ActivationCodeService;
import com.bsujava.servlet.service.AuthService;
import com.bsujava.servlet.service.impl.ActivationCodeServiceImpl;
import com.bsujava.servlet.service.impl.AuthServiceImpl;
import com.bsujava.servlet.validator.UserValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private AuthService authService;
    private ActivationCodeService activationCodeService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        authService = new AuthServiceImpl();
        activationCodeService = new ActivationCodeServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/auth/register.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        if (!password.equals(repeatPassword)) {
            request.setAttribute("error", "The password must be the same!");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            return;
        }
        try {
            User user = new User(username, password);
            user.setEmail(email);
            new UserValidator().validate(user);
            authService.register(user);
            activationCodeService.generateAndSendVerificationCode(user);
            response.sendRedirect(request.getContextPath() + "/auth/confirmEmail.jsp");
        } catch (DatabaseException | IllegalArgumentException e) {
            request.setAttribute("error", "Registration failed: " + e.getMessage());
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
        }
    }
}
