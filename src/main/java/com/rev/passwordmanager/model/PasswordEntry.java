package com.rev.passwordmanager.model;

public class PasswordEntry {

    private int id;
    private int userId;
    private String accountName;
    private String accountUsername;
    private String accountPassword;

    // Constructor for INSERT (id will be auto-generated)
    public PasswordEntry(int userId, String accountName,
                         String accountUsername, String accountPassword) {
        this.userId = userId;
        this.accountName = accountName;
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
    }

    // Constructor for FETCH (with id)
    public PasswordEntry(int id, int userId, String accountName,
                         String accountUsername, String accountPassword) {
        this.id = id;
        this.userId = userId;
        this.accountName = accountName;
        this.accountUsername = accountUsername;
        this.accountPassword = accountPassword;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }
}
