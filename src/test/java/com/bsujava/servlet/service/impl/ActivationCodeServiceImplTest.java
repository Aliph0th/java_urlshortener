package com.bsujava.servlet.service.impl;

import com.bsujava.servlet.dao.ActivationCodeDao;
import com.bsujava.servlet.dao.UserDao;
import com.bsujava.servlet.entity.ActivationCode;
import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.util.EmailSenderUtil;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class ActivationCodeServiceImplTest {
    private ActivationCodeServiceImpl service;
    private ActivationCodeDao activationCodeDao;
    private UserDao userDao;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        activationCodeDao = mock(ActivationCodeDao.class);
        userDao = mock(UserDao.class);
        service = new ActivationCodeServiceImpl(activationCodeDao, userDao);
    }

    @Test
    public void testGenerateAndSendVerificationCode() throws Exception {
        User user = new User("bob", "pwd");
        user.setId(42);
        user.setEmail("bob@example.com");

        try (MockedStatic<EmailSenderUtil> mail = Mockito.mockStatic(EmailSenderUtil.class)) {
            service.generateAndSendVerificationCode(user);
            verify(activationCodeDao).save(eq(42), anyString(), any(LocalDateTime.class));
            mail.verify(() -> EmailSenderUtil.sendConfirmationEmail("bob@example.com", anyString()));
        }
    }

    @Test
    public void testVerifyCodeSuccess() throws DatabaseException {
        String token = "tok123";
        ActivationCode ac = new ActivationCode(token, LocalDateTime.now().plusHours(1), 99);
        ac.setId(7);
        when(activationCodeDao.findByCode(token)).thenReturn(ac);

        boolean ok = service.verifyCode(token);

        assertTrue(ok);
        verify(userDao).updateEnabled(99);
        verify(activationCodeDao).delete(99);
    }

    @Test
    public void testVerifyCodeFailsOnNullOrExpired() throws DatabaseException {
        when(activationCodeDao.findByCode("bad")).thenReturn(null);
        assertFalse(service.verifyCode("bad"));
        verify(userDao, never()).updateEnabled(anyInt());

        ActivationCode expired = new ActivationCode("t", LocalDateTime.now().minusMinutes(1), 1);
        when(activationCodeDao.findByCode("t")).thenReturn(expired);
        assertFalse(service.verifyCode("t"));
        verify(userDao, never()).updateEnabled(anyInt());
    }
}
