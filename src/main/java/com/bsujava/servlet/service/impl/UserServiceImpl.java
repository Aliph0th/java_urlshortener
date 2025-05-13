package com.bsujava.servlet.service.impl;

import com.bsujava.servlet.dao.UserDao;
import com.bsujava.servlet.dao.impl.UserDaoImpl;
import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl() {
        this(new UserDaoImpl());
    }

    @Override
    public int save(String username, String email, String password) throws DatabaseException {
        return userDao.save(username, email, password);
    }

    @Override
    public User findByUsername(String username) throws DatabaseException {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean isUsernameAvailable(String username) throws DatabaseException {
        try {
            User user = userDao.findByUsername(username);
            return user == null;
        } catch (DatabaseException e) {
            return true;
        }
    }

    @Override
    public void updateUser(int userId, String newUsername, String newAvatarUrl) throws DatabaseException {
        userDao.updateUser(userId, newUsername, newAvatarUrl);
    }
}
