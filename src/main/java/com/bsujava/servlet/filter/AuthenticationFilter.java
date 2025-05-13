package com.bsujava.servlet.filter;

import com.bsujava.servlet.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    private static final String[] PUBLIC_PATHS = {
            "^/login$",
            "^/register$",
            "^/confirm$",
            "^/auth/.*",
            "^/css/.*",
            "^/images/.*",
            "^/js/.*",
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().replace(httpRequest.getContextPath(), "");

        HttpSession session = httpRequest.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;
        boolean isAuthorized = user != null && user.isEnabled();

        boolean isPublic = Arrays.stream(PUBLIC_PATHS)
                .anyMatch(pattern -> path.matches(pattern));

        if (isAuthorized || isPublic) {
            request.setCharacterEncoding("UTF-8");
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/auth/login.jsp");
        }
    }
}
