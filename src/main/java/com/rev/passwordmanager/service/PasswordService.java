package com.rev.passwordmanager.service;

import com.rev.passwordmanager.dao.PasswordEntryDAO;
import com.rev.passwordmanager.model.PasswordEntry;
import com.rev.passwordmanager.Util.EncryptionUtil;
import com.rev.passwordmanager.Util.PasswordGenerator;

import java.util.List;
import java.util.Optional;

public class PasswordService {

    private final PasswordEntryDAO passwordDAO = new PasswordEntryDAO();

    // ---------------- Add password entry ----------------
    public boolean addPasswordEntry(int userId, String title, String username, String password, boolean autoGenerate) {
        try {
            if (autoGenerate) password = PasswordGenerator.generatePassword(12);
            String encrypted = EncryptionUtil.encrypt(password);

            PasswordEntry entry = new PasswordEntry(userId, title, username, encrypted);
            return passwordDAO.addPasswordEntry(entry);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- Get all password entries ----------------
    public List<PasswordEntry> getPasswordEntries(int userId) {
        return passwordDAO.getAllPasswordsByUserId(userId);
    }

    // ---------------- Search entry by title ----------------
    public Optional<PasswordEntry> searchEntry(int userId, String title) {
        return passwordDAO.getAllPasswordsByUserId(userId).stream()
                .filter(e -> e.getAccountName().equalsIgnoreCase(title))
                .findFirst();
    }

    // ---------------- Decrypt password ----------------
    public String decryptPassword(String encrypted) {
        try {
            return EncryptionUtil.decrypt(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    // ---------------- Update password entry ----------------
    public boolean updatePasswordEntry(int passwordId, String title, String username, String password) {
        try {
            String encrypted = EncryptionUtil.encrypt(password);
            return passwordDAO.updatePasswordEntry(new PasswordEntry(passwordId, 0, title, username, encrypted));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ---------------- Delete password entry ----------------
    public boolean deletePasswordEntry(int passwordId) {
        return passwordDAO.deletePasswordEntry(passwordId);
    }
}
