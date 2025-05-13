package com.bsujava.servlet.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User {
    private int id;

    @NotBlank(message = "The username must not be empty")
    @Size(min = 5, max = 40, message = "The username must contain from 2 to 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "The username must not contain special symbols")
    private String username;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "The password must not be empty")
    @Size(min = 8, max = 64, message = "The password must contain at least 8 symbols and a maximum of 64 symbols")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "The password must contain latins characters, numbers or \"_\"")
    private String password;

    private boolean enabled = false;
    private String avatarUrl;

    private byte[] encryptedPassword;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
