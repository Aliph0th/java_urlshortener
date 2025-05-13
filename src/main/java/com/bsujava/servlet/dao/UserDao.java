package com.bsujava.servlet.dao;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;

public interface UserDao {
    User findByUsername(String username) throws DatabaseException;
    int save(String username, String email, String password) throws DatabaseException;
    void updateEnabled(int userId) throws DatabaseException;
    void updateUser(int userId, String newUsername, String newAvatarUrl) throws DatabaseException;
}
