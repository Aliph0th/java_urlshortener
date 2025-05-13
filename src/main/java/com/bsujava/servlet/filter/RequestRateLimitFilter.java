package com.bsujava.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class RequestRateLimitFilter implements Filter {
    private static final long MIN_INTERVAL = 10;
    private static final int TOO_MANY_REQUESTS = 429;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            HttpSession session = req.getSession();
            Long lastRequest = (Long) session.getAttribute("lastRequestTime");
            long now = System.currentTimeMillis();
            if (lastRequest != null && now - lastRequest < MIN_INTERVAL) {
                ((HttpServletResponse) response).sendError(TOO_MANY_REQUESTS, "Too many requests");
                return;
            }
            session.setAttribute("lastRequestTime", now);
        }
        chain.doFilter(request, response);
    }
}
