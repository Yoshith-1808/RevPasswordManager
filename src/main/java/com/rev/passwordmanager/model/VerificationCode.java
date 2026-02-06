package com.rev.passwordmanager.model;

import java.time.LocalDateTime;

public class VerificationCode {

    private int id;
    private int userId;
    private String code;
    private LocalDateTime expiry;

    public VerificationCode() {}

    public VerificationCode(int userId, String code, LocalDateTime expiry) {
        this.userId = userId;
        this.code = code;
        this.expiry = expiry;
    }

    public VerificationCode(int id, int userId, String code, LocalDateTime expiry) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.expiry = expiry;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }
    public void setExpiry(LocalDateTime expiry) {
        this.expiry = expiry;
    }
}
