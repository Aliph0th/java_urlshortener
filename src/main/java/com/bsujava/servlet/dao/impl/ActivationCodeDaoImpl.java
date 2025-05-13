package com.bsujava.servlet.dao.impl;

import com.bsujava.servlet.dao.ActivationCodeDao;
import com.bsujava.servlet.entity.ActivationCode;
import com.bsujava.servlet.exception.DatabaseException;
import java.sql.*;
import java.time.LocalDateTime;

public class ActivationCodeDaoImpl extends BaseDao implements ActivationCodeDao {

    @Override
    public void save(int userId, String code, LocalDateTime expiration) throws DatabaseException {
        String query = "INSERT INTO activation_codes (user_id, code, expiration) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setString(2, code);
            statement.setTimestamp(3, Timestamp.valueOf(expiration));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to save activation code", e);
        }
    }

    @Override
    public ActivationCode findByCode(String code) throws DatabaseException {
        String query = "SELECT id, user_id, code, expiration FROM activation_codes WHERE code = ?";
        ActivationCode activationCode = null;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    activationCode = new ActivationCode(
                            resultSet.getString("code"),
                            resultSet.getTimestamp("expiration").toLocalDateTime(),
                            resultSet.getInt("user_id")
                    );
                    activationCode.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find activation code", e);
        }
        return activationCode;
    }

    @Override
    public void delete(int userId) throws DatabaseException {
        String query = "DELETE FROM activation_codes WHERE user_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete activation code", e);
        }
    }
}