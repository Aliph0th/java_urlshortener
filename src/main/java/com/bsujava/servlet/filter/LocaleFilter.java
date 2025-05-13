package com.bsujava.servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebFilter("/*")
public class LocaleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        String lang = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = req.getLocale().getLanguage();
            session.setAttribute("lang", lang);
        }

        request.setAttribute("lang", lang);

        chain.doFilter(request, response);
    }
}
