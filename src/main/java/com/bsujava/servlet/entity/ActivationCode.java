package com.bsujava.servlet.entity;

import java.time.LocalDateTime;

public class ActivationCode {
    private int id;
    private String code;
    private LocalDateTime expiration;
    private int userId;

    public ActivationCode() {}

    public ActivationCode(String code, LocalDateTime expiration, int userId) {
        this.code = code;
        this.expiration = expiration;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

