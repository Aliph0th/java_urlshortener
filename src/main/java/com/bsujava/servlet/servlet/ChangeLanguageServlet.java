package com.bsujava.servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebServlet("/change-language")
public class ChangeLanguageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("lang");
        HttpSession session = request.getSession();

        if ("ru".equals(lang)) {
            session.setAttribute("lang", "ru");
        } else {
            session.setAttribute("lang", "en");
        }

        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath());
    }
}
