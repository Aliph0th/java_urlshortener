package com.bsujava.servlet.service;

import com.bsujava.servlet.dao.ShortUrlDao;
import com.bsujava.servlet.dao.impl.ShortUrlDaoImpl;
import com.bsujava.servlet.model.ShortUrl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

public class UrlShortenerService {
    private static final Logger LOGGER = LogManager.getLogger(UrlShortenerService.class);
    private static final String ALPHABET = "23456789BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz";
    private static final int CODE_LENGTH = 6;
    
    private final ShortUrlDao shortUrlDao;
    private final SecureRandom random;

    public UrlShortenerService() {
        this.shortUrlDao = new ShortUrlDaoImpl();
        this.random = new SecureRandom();
    }

    public ShortUrl createShortUrl(String originalUrl, int userId) {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (shortUrlDao.findByShortCode(shortCode).isPresent());

        ShortUrl shortUrl = new ShortUrl(originalUrl, shortCode, userId);
        return shortUrlDao.save(shortUrl);
    }

    public Optional<ShortUrl> getOriginalUrl(String shortCode) {
        Optional<ShortUrl> shortUrl = shortUrlDao.findByShortCode(shortCode);
        shortUrl.ifPresent(url -> shortUrlDao.incrementClickCount(shortCode));
        return shortUrl;
    }

    public List<ShortUrl> getUserUrls(int userId) {
        return shortUrlDao.findByUserId(userId);
    }

    private String generateShortCode() {
        StringBuilder shortCode = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            shortCode.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return shortCode.toString();
    }

    public boolean deleteUrl(String shortCode, int userId) {
        return shortUrlDao.deleteByShortCode(shortCode, userId);
    }
} 
