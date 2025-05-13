package com.bsujava.servlet.servlet;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.UserService;
import com.bsujava.servlet.service.impl.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

@WebServlet("/profile/edit")
@MultipartConfig
public class EditProfileServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();
    private final String IMAGES_FOLDER = "images";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/profile/edit-profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String newUsername = request.getParameter("newUsername");
        String newAvatarUrl = user.getAvatarUrl();
        Part filePart = request.getPart("newAvatar");

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + getFileName(filePart);
            String applicationPath = request.getServletContext().getRealPath("");
            String imagesPath = applicationPath + File.separator + IMAGES_FOLDER;
            File uploadDir = new File(imagesPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            filePart.write(imagesPath + File.separator + fileName);
            newAvatarUrl = request.getContextPath() + "/" + IMAGES_FOLDER + "/" + fileName;
        }

        try {
            userService.updateUser(user.getId(), newUsername, newAvatarUrl);
            user.setUsername(newUsername);
            user.setAvatarUrl(newAvatarUrl);
            response.sendRedirect(request.getContextPath() + "/profile");
        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
