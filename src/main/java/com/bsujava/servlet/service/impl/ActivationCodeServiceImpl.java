package com.bsujava.servlet.service.impl;

import com.bsujava.servlet.dao.ActivationCodeDao;
import com.bsujava.servlet.dao.UserDao;
import com.bsujava.servlet.dao.impl.ActivationCodeDaoImpl;
import com.bsujava.servlet.dao.impl.UserDaoImpl;
import com.bsujava.servlet.entity.ActivationCode;
import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.ActivationCodeService;
import com.bsujava.servlet.util.EmailSenderUtil;
import com.bsujava.servlet.util.VerificationUtil;

import java.time.LocalDateTime;

public class ActivationCodeServiceImpl implements ActivationCodeService {
    private final ActivationCodeDao activationCodeDao;
    private final UserDao userDao;

    public ActivationCodeServiceImpl(ActivationCodeDao activationCodeDao, UserDao userDao) {
        this.activationCodeDao = activationCodeDao;
        this.userDao = userDao;
    }
    public ActivationCodeServiceImpl() {
        this(new ActivationCodeDaoImpl(), new UserDaoImpl());
    }

    @Override
    public void generateAndSendVerificationCode(User user) throws DatabaseException {
        String token = VerificationUtil.generateVerificationToken();
        LocalDateTime expiration = LocalDateTime.now().plusHours(24);

        activationCodeDao.save(user.getId(), token, expiration);

        try {
            EmailSenderUtil.sendConfirmationEmail(user.getEmail(), token);
        } catch (Exception e) {
            throw new DatabaseException("Failed to send confirmation email", e);
        }
    }

    @Override
    public boolean verifyCode(String token) throws DatabaseException {
        ActivationCode activationCode = activationCodeDao.findByCode(token);
        if (activationCode == null || activationCode.getExpiration().isBefore(LocalDateTime.now())) {
            return false;
        }

        int userId = activationCode.getUserId();
        userDao.updateEnabled(userId);
        activationCodeDao.delete(userId);
        return true;
    }
}
