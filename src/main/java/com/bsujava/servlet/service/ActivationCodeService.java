package com.bsujava.servlet.service;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;

public interface ActivationCodeService {
    void generateAndSendVerificationCode(User user) throws DatabaseException;
    boolean verifyCode(String token) throws DatabaseException;
}
