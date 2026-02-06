package com.rev.passwordmanager.model;

public class User {
    private int id;
    private String username;
    private String email;
    private String masterPasswordHash;

    public User() {}

    public User(String username, String email, String masterPasswordHash) {
        this.username = username;
        this.email = email;
        this.masterPasswordHash = masterPasswordHash;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMasterPasswordHash() { return masterPasswordHash; }
    public void setMasterPasswordHash(String masterPasswordHash) { this.masterPasswordHash = masterPasswordHash; }
}
