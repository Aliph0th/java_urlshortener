package com.bsujava.servlet.servlet;

import com.bsujava.servlet.entity.Contact;
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
import java.util.List;

@WebServlet("/contacts")
public class ContactListServlet extends HttpServlet {
    private final ContactService contactService = new ContactServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login.jsp");
            return;
        }
        int page = 1;
        int pageSize = 5;
        String pageParam = req.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {}
        }
        try {
            List<Contact> contacts = contactService.findContactsByUserIdPaged(user.getId(), page, pageSize);
            int totalContacts = contactService.countContactsByUserId(user.getId());
            int totalPages = (int) Math.ceil((double) totalContacts / pageSize);
            req.setAttribute("contacts", contacts);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.getRequestDispatcher("/contacts/contacts.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            throw new ServletException("Unable to load contacts", e);
        }
    }
}
