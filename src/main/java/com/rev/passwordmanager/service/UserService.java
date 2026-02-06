package com.rev.passwordmanager.service;

import com.rev.passwordmanager.dao.UserDAO;
import com.rev.passwordmanager.model.User;
import com.rev.passwordmanager.Util.HashUtil;

import java.util.Optional;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    // ---------------- Register user ----------------
    public boolean registerUser(String username, String email, String masterPassword) {
        String hash = HashUtil.hashString(masterPassword);
        User user = new User(username, email, hash);
        return userDAO.createUser(user);
    }

    // ---------------- Login user ----------------
    public boolean authenticateUser(String username, String password) {
        Optional<User> userOpt = getUserByUsername(username);
        if (userOpt.isEmpty()) return false;

        User user = userOpt.get();
        return HashUtil.verifyHash(password, user.getMasterPasswordHash());
    }

    // ---------------- Get user by username ----------------
    public Optional<User> getUserByUsername(String username) {
        User user = userDAO.getUserByUsername(username);
        return Optional.ofNullable(user);
    }

    // ---------------- Get user by ID ----------------
    public Optional<User> getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        return Optional.ofNullable(user);
    }

    // ---------------- Update master password ----------------
    public boolean updatePassword(int userId, String newPassword) {
        String hash = HashUtil.hashString(newPassword);
        return userDAO.updateMasterPassword(userId, hash);
    }

    // ---------------- Delete user ----------------
    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId); // Add this method in DAO if missing
    }

    // ---------------- Login and return User ----------------
    public User login(String username, String password) {
        if (authenticateUser(username, password)) {
            return getUserByUsername(username).orElse(null);
        }
        return null;
    }
}
