package com.bsujava.servlet.service.impl;

import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.UserService;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class AuthServiceImplTest {
    private AuthServiceImpl auth;
    private UserService userService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = mock(UserService.class);
        auth = new AuthServiceImpl(userService);
    }

    @Test
    public void testRegisterSuccess() throws DatabaseException {
        User user = new User("alex", "pass");
        user.setEmail("a@a.com");
        when(userService.isUsernameAvailable("alex")).thenReturn(true);
        when(userService.save("alex", "a@a.com", "pass")).thenReturn(123);

        auth.register(user);
        assertEquals(user.getId(), 123);
        verify(userService).save("alex", "a@a.com", "pass");
    }

    @Test(expectedExceptions = DatabaseException.class)
    public void testRegisterUsernameTaken() throws DatabaseException {
        User user = new User("alex", "pass");
        when(userService.isUsernameAvailable("alex")).thenReturn(false);
        auth.register(user);
    }

    @Test
    public void testLoginSuccess() throws DatabaseException {
        when(userService.findByUsername("u")).thenReturn(new User("u","p"));
        auth.login("u", "p");
    }

    @Test(expectedExceptions = DatabaseException.class)
    public void testLoginFails() throws DatabaseException {
        when(userService.findByUsername("u")).thenReturn(null);
        auth.login("u", "x");
    }
}
