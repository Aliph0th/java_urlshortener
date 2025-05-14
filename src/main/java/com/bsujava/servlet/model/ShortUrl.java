package com.bsujava.servlet.model;

import java.time.LocalDateTime;

public class ShortUrl {
    private Long id;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt;
    private int userId;
    private int clickCount;

    public ShortUrl() {
    }

    public ShortUrl(String originalUrl, String shortCode, int userId) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.clickCount = 0;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public void incrementClickCount() {
        this.clickCount++;
    }
} 
