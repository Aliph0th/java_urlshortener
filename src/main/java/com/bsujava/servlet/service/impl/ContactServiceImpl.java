package com.bsujava.servlet.service.impl;

import com.bsujava.servlet.dao.ContactDao;
import com.bsujava.servlet.dao.impl.ContactDaoImpl;
import com.bsujava.servlet.entity.Contact;
import com.bsujava.servlet.exception.DatabaseException;
import com.bsujava.servlet.service.ContactService;
import java.util.List;

public class ContactServiceImpl implements ContactService {
    private final ContactDao contactDao;

    public ContactServiceImpl(ContactDao contactDao) {
        this.contactDao = contactDao;
    }
    public ContactServiceImpl() {
        this(new ContactDaoImpl());
    }

    @Override
    public List<Contact> findContactsByUserIdPaged(int userId, int page, int pageSize) throws DatabaseException {
        int offset = (page - 1) * pageSize;
        return contactDao.findContactsByUserIdPaged(userId, offset, pageSize);
    }

    @Override
    public int countContactsByUserId(int userId) throws DatabaseException {
        return contactDao.countContactsByUserId(userId);
    }

    @Override
    public void deleteContact(int contactId, int userId) throws DatabaseException {
        contactDao.deleteContact(contactId, userId);
    }

    @Override
    public void addContact(Contact contact) throws DatabaseException {
        contactDao.addContact(contact);
    }
}
