package com.bsujava.servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/double")
public class NumberDoublerServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/numberForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String numberStr = request.getParameter("number");
        
        try {
            double number = Double.parseDouble(numberStr);
            double result = number * 2;
            
            // Set attributes to be used in JSP
            request.setAttribute("originalNumber", numberStr);
            request.setAttribute("result", String.valueOf(result));
            
            // Forward back to the JSP page with the results
            request.getRequestDispatcher("/numberForm.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
        }
    }
} 
