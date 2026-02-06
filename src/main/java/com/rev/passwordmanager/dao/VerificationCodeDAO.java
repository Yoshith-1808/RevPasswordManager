package com.rev.passwordmanager.dao;

import com.rev.passwordmanager.model.VerificationCode;
import com.rev.passwordmanager.Util.DBConnection;

import java.sql.*;

public class VerificationCodeDAO {

    public boolean saveVerificationCode(VerificationCode vc) {
        String sql = "INSERT INTO verification_codes (user_id, code, expiry) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, vc.getUserId());
            ps.setString(2, vc.getCode());
            ps.setTimestamp(3, Timestamp.valueOf(vc.getExpiry()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) vc.setId(rs.getInt(1)); // FIXED
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public VerificationCode getLatestCodeByUserId(int userId) {
        String sql = "SELECT * FROM verification_codes WHERE user_id=? ORDER BY expiry DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                VerificationCode vc = new VerificationCode();
                vc.setId(rs.getInt("id"));       // FIXED
                vc.setUserId(rs.getInt("user_id"));
                vc.setCode(rs.getString("code"));
                vc.setExpiry(rs.getTimestamp("expiry").toLocalDateTime());
                return vc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
