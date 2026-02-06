package com.rev.passwordmanager.dao;

import com.rev.passwordmanager.model.PasswordEntry;
import com.rev.passwordmanager.Util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PasswordEntryDAO {

    // Add a new password entry
    public boolean addPasswordEntry(PasswordEntry entry) {
        String sql = "INSERT INTO password_entries (user_id, account_name, account_username, account_password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, entry.getUserId());
            ps.setString(2, entry.getAccountName());
            ps.setString(3, entry.getAccountUsername());
            ps.setString(4, entry.getAccountPassword());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) return false;

            // Get generated ID and set it to the entry
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entry.setId(rs.getInt(1));
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all passwords of a user
    public List<PasswordEntry> getAllPasswordsByUserId(int userId) {
        List<PasswordEntry> list = new ArrayList<>();
        String sql = "SELECT * FROM password_entries WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PasswordEntry entry = new PasswordEntry(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("account_name"),
                        rs.getString("account_username"),
                        rs.getString("account_password")
                );
                list.add(entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Fetch password by ID
    public PasswordEntry getPasswordById(int passwordId) {
        String sql = "SELECT * FROM password_entries WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passwordId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PasswordEntry(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("account_name"),
                        rs.getString("account_username"),
                        rs.getString("account_password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update an existing password entry
    public boolean updatePasswordEntry(PasswordEntry entry) {
        String sql = "UPDATE password_entries SET account_name=?, account_username=?, account_password=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, entry.getAccountName());
            ps.setString(2, entry.getAccountUsername());
            ps.setString(3, entry.getAccountPassword());
            ps.setInt(4, entry.getId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete a password entry
    public boolean deletePasswordEntry(int passwordId) {
        String sql = "DELETE FROM password_entries WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, passwordId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
