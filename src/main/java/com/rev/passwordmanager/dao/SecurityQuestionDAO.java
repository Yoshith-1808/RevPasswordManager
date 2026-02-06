package com.rev.passwordmanager.dao;

import com.rev.passwordmanager.model.SecurityQuestion;
import com.rev.passwordmanager.Util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SecurityQuestionDAO {

    public boolean addSecurityQuestion(SecurityQuestion sq) {
        String sql = "INSERT INTO security_questions (user_id, question, answer) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, sq.getUserId());
            ps.setString(2, sq.getQuestion());
            ps.setString(3, sq.getAnswer());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) sq.setId(rs.getInt(1));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<SecurityQuestion> getQuestionsByUserId(int userId) {
        List<SecurityQuestion> list = new ArrayList<>();
        String sql = "SELECT * FROM security_questions WHERE user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SecurityQuestion sq = new SecurityQuestion();
                sq.setId(rs.getInt("id"));
                sq.setUserId(rs.getInt("user_id"));
                sq.setQuestion(rs.getString("question"));
                sq.setAnswer(rs.getString("answer"));
                list.add(sq);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
