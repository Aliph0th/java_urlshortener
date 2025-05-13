package com.bsujava.servlet.service.impl;

import com.bsujava.servlet.dao.ContactDao;
import com.bsujava.servlet.entity.Contact;
import com.bsujava.servlet.exception.DatabaseException;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class ContactServiceImplTest {
    private ContactServiceImpl service;
    private ContactDao dao;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dao = mock(ContactDao.class);
        service = new ContactServiceImpl(dao);
    }

    @Test
    public void testFindContactsByUserIdPaged() throws DatabaseException {
        List<Contact> mock = List.of(new Contact(1,2,"n","p","e"));
        when(dao.findContactsByUserIdPaged(2, 0, 10)).thenReturn(mock);

        var result = service.findContactsByUserIdPaged(2,1,10);
        assertEquals(result, mock);
        verify(dao).findContactsByUserIdPaged(2,0,10);
    }

    @Test
    public void testCountContactsByUserId() throws DatabaseException {
        when(dao.countContactsByUserId(5)).thenReturn(7);
        assertEquals(service.countContactsByUserId(5), 7);
    }

    @Test
    public void testAddAndDelete() throws DatabaseException {
        Contact c = new Contact(5,5,"n","p","e");
        service.addContact(c);
        verify(dao).addContact(c);

        service.deleteContact(9,5);
        verify(dao).deleteContact(9,5);
    }
}
