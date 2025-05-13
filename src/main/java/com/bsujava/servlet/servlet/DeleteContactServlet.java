package com.bsujava.servlet.servlet;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.ContactService;
import com.bsujava.servlet.service.impl.ContactServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/contacts/delete")
public class DeleteContactServlet extends HttpServlet {
    private final ContactService contactService = new ContactServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login.jsp");
            return;
        }

        int contactId = Integer.parseInt(req.getParameter("contactId"));
        try {
            contactService.deleteContact(contactId, user.getId());
            resp.sendRedirect(req.getContextPath() + "/contacts");
        } catch (DatabaseException e) {
            throw new ServletException("Unable to delete contact", e);
        }
    }
}

