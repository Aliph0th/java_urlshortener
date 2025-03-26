package com.webjavabsu2ndcourse4ndterm.exceptions;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json");
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (HttpException e) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(e.getStatus());
            JSONObject obj = new JSONObject();
            obj.put("status", e.getStatus());
            obj.put("message", e.getMessage());
            response.getWriter().write(obj.toString());
        } catch (Exception e) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject obj = new JSONObject();
            obj.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            obj.put("message", e.getMessage());
            response.getWriter().write(obj.toString());
        }
    }
}
