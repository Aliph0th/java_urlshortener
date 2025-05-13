package com.bsujava.servlet.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@WebServlet("/page")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Logger logger = LogManager.getLogger(MyServlet.class);

        resp.setContentType("text/html");

        String input = req.getParameter("input");
        logger.info("Received input: " + input);

        if (input == null) {
            input = "NO INPUT PROVIDED";
            logger.info("No input provided");
        }

        String output;
        try {
            LocalDate date = LocalDate.parse(input, DateTimeFormatter.ISO_DATE);
            logger.info("Parsed date: " + date);

            LocalDate newDate = date.plusDays(7);
            logger.info("New date after adding 7 days: " + newDate);

            output = newDate.format(DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: " + input, e);
            output = "INVALID DATE FORMAT. Expected format: yyyy-MM-dd";
        }

        PrintWriter out = resp.getWriter();
        out.println("<h1>Original: " + input + "</h1>");
        out.println("<h1>Processed: " + output + "</h1>");
        out.flush();
    }
}