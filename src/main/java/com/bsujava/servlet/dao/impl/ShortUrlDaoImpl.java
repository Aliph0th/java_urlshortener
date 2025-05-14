package com.bsujava.servlet.dao.impl;

import com.bsujava.servlet.dao.ShortUrlDao;
import com.bsujava.servlet.model.ShortUrl;
import com.bsujava.servlet.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShortUrlDaoImpl implements ShortUrlDao {
    private static final Logger LOGGER = LogManager.getLogger(ShortUrlDaoImpl.class);
    private final ConnectionPool connectionPool;

    public ShortUrlDaoImpl() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public ShortUrl save(ShortUrl shortUrl) {
        String sql = "INSERT INTO short_urls (original_url, short_code, user_id, created_at) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, shortUrl.getOriginalUrl());
            stmt.setString(2, shortUrl.getShortCode());
            stmt.setInt(3, shortUrl.getUserId());
            stmt.setTimestamp(4, Timestamp.valueOf(shortUrl.getCreatedAt()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating short URL failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    shortUrl.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating short URL failed, no ID obtained.");
                }
            }
            
            return shortUrl;
        } catch (SQLException e) {
            LOGGER.error("Error saving short URL", e);
            throw new RuntimeException("Error saving short URL", e);
        }
    }

    @Override
    public Optional<ShortUrl> findByShortCode(String shortCode) {
        String sql = "SELECT * FROM short_urls WHERE short_code = ?";
        
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, shortCode);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToShortUrl(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding short URL by code", e);
            throw new RuntimeException("Error finding short URL by code", e);
        }
        
        return Optional.empty();
    }

    @Override
    public List<ShortUrl> findByUserId(int userId) {
        String sql = "SELECT * FROM short_urls WHERE user_id = ? ORDER BY created_at DESC";
        List<ShortUrl> urls = new ArrayList<>();
        
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    urls.add(mapResultSetToShortUrl(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error finding URLs by user ID", e);
            throw new RuntimeException("Error finding URLs by user ID", e);
        }
        
        return urls;
    }

    @Override
    public void incrementClickCount(String shortCode) {
        String sql = "UPDATE short_urls SET click_count = click_count + 1 WHERE short_code = ?";
        
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, shortCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Error incrementing click count", e);
            throw new RuntimeException("Error incrementing click count", e);
        }
    }

    private ShortUrl mapResultSetToShortUrl(ResultSet rs) throws SQLException {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(rs.getLong("id"));
        shortUrl.setOriginalUrl(rs.getString("original_url"));
        shortUrl.setShortCode(rs.getString("short_code"));
        shortUrl.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        shortUrl.setUserId(rs.getInt("user_id"));
        shortUrl.setClickCount(rs.getInt("click_count"));
        return shortUrl;
    }
} 
