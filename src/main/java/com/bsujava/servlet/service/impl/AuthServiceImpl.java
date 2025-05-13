package com.bsujava.servlet.service.impl;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.AuthService;
import com.bsujava.servlet.service.UserService;

public class AuthServiceImpl implements AuthService {
    private UserService userService;
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }
    public AuthServiceImpl() {
        this(new UserServiceImpl());
    }

    @Override
    public void register(User user) throws DatabaseException {
        boolean isUsernameAvailable = userService.isUsernameAvailable(user.getUsername());
        if (!isUsernameAvailable) {
            throw new DatabaseException("Username is already taken");
        }
        int generatedId = userService.save(user.getUsername(), user.getEmail(), user.getPassword());
        user.setId(generatedId);
    }

    @Override
    public void login(String username, String password) throws DatabaseException {
        User user = userService.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new DatabaseException("Invalid username or password");
        }
    }
}
