package com.bsujava.servlet.dao.impl;

import com.bsujava.servlet.dao.ContactDao;
import com.bsujava.servlet.entity.Contact;
import com.bsujava.servlet.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl extends BaseDao implements ContactDao {

    @Override
    public List<Contact> findContactsByUserIdPaged(int userId, int offset, int limit) throws DatabaseException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE user_id = ? LIMIT ? OFFSET ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    contacts.add(new Contact(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("email")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch paginated contacts", e);
        }

        return contacts;
    }

    @Override
    public int countContactsByUserId(int userId) throws DatabaseException {
        String sql = "SELECT COUNT(*) FROM contacts WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to count contacts", e);
        }
        return 0;
    }

    @Override
    public void deleteContact(int id, int userId) throws DatabaseException {
        String sql = "DELETE FROM contacts WHERE id = ? AND user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete contact", e);
        }
    }

    @Override
    public void addContact(Contact contact) throws DatabaseException {
        String sql = "INSERT INTO contacts (user_id, name, phone, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, contact.getUserId());
            stmt.setString(2, contact.getName());
            stmt.setString(3, contact.getPhone());
            stmt.setString(4, contact.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to add contact", e);
        }
    }
}
