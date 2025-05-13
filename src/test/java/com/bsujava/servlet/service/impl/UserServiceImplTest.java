package com.bsujava.servlet.service.impl;

import com.bsujava.servlet.dao.UserDao;
import com.bsujava.servlet.entity.User;
import com.bsujava.servlet.exception.DatabaseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class UserServiceImplTest {
    private UserServiceImpl service;
    private UserDao dao;

    @BeforeMethod
    public void setUp() {
        dao = mock(UserDao.class);
        service = new UserServiceImpl(dao);
    }

    @Test
    public void testSaveDelegatesToDao() throws DatabaseException {
        when(dao.save("u", "e@e.com", "pwd")).thenReturn(77);
        int id = service.save("u","e@e.com","pwd");
        assertEquals(id, 77);
        verify(dao).save("u","e@e.com","pwd");
    }

    @Test
    public void testFindByUsernameDelegates() throws DatabaseException {
        User u = new User("bob","x");
        when(dao.findByUsername("bob")).thenReturn(u);
        assertSame(service.findByUsername("bob"), u);
        verify(dao).findByUsername("bob");
    }

    @Test
    public void testIsUsernameAvailableWhenDaoReturnsNull() throws DatabaseException {
        when(dao.findByUsername("new")).thenReturn(null);
        assertTrue(service.isUsernameAvailable("new"));
    }

    @Test
    public void testIsUsernameAvailableWhenExists() throws DatabaseException {
        when(dao.findByUsername("old")).thenReturn(new User("old","pw"));
        assertFalse(service.isUsernameAvailable("old"));
    }

    @Test
    public void testUpdateUserDelegates() throws DatabaseException {
        service.updateUser(5, "n", "a.png");
        verify(dao).updateUser(5, "n", "a.png");
    }
}
