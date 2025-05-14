package com.bsujava.servlet.dao;

import com.bsujava.servlet.model.ShortUrl;
import java.util.List;
import java.util.Optional;

public interface ShortUrlDao {
    ShortUrl save(ShortUrl shortUrl);
    Optional<ShortUrl> findByShortCode(String shortCode);
    List<ShortUrl> findByUserId(int userId);
    void incrementClickCount(String shortCode);
} 
