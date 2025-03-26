package com.webjavabsu2ndcourse4ndterm.services;

import com.webjavabsu2ndcourse4ndterm.exceptions.HttpException;
import com.webjavabsu2ndcourse4ndterm.shortener.models.User;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Objects;

public class UserService {
    private final DatabaseService databaseService = new DatabaseService();

    public JSONObject createUser(JSONObject jsonInput) throws HttpException {
        String username = jsonInput.optString("username");
        if (Objects.equals(username, "")) {
            throw new HttpException("Username cannot be empty");
        }
        String email = jsonInput.optString("email");
        if (Objects.equals(email, "")) {
            throw new HttpException("No email specified");
        }
        String password = jsonInput.optString("password");
        if (Objects.equals(password, "") || !User.isPasswordComplex(password)) {
            throw new HttpException("Password is not a complex");
        }
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = databaseService.insertUser(username, email, hashedPassword);

        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("id", user.getId());
        jsonResponse.put("username", user.getUsername());
        jsonResponse.put("email", user.getEmail());

        return jsonResponse;
    }

    public JSONObject getUser(Long userId) throws HttpException {
        JSONObject jsonResponse = new JSONObject();
        User user = databaseService.getUserById(userId);
        if (user == null) {
            throw new HttpException("User not found", HttpServletResponse.SC_NOT_FOUND);
        }
        jsonResponse.put("user", user);

        return jsonResponse;
    }

    public JSONArray getUsers() throws HttpException {
        List<User> users = databaseService.getAllUsers();

        return new JSONArray(users);
    }

    public void deleteUser(Long userId) throws HttpException {
        boolean deleted = databaseService.deleteUser(userId);
        if (!deleted) {
            throw new HttpException("User not found", HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
