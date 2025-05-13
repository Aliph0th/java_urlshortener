package com.bsujava.servlet.service;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;

public interface AuthService {
    void register (User user) throws DatabaseException;
    void login (String username, String password) throws DatabaseException;
}
