package com.webjavabsu2ndcourse4ndterm.shortener.models;

import jakarta.persistence.*;

@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 2048)
    private String username;

    @Column(name = "email", nullable = false, length = 2048, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // Конструкторы
    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean isPasswordComplex(String password) {
        if (password.length() < 8) return false;
        if (!password.matches(".*[A-Z].*")) return false;
        if (!password.matches(".*[0-9].*")) return false;
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"); // Специальные символы
    }
}