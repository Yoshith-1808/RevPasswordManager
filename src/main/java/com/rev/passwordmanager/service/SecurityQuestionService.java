package com.rev.passwordmanager.service;

import com.rev.passwordmanager.dao.SecurityQuestionDAO;
import com.rev.passwordmanager.model.SecurityQuestion;
import com.rev.passwordmanager.Util.HashUtil;

import java.util.List;

public class SecurityQuestionService {

    private final SecurityQuestionDAO sqDAO = new SecurityQuestionDAO();

    // ---------------- Add security question ----------------
    public boolean addSecurityQuestion(int userId, String question, String answer) {
        String hashedAnswer = HashUtil.hashString(answer);
        SecurityQuestion sq = new SecurityQuestion(userId, question, hashedAnswer);
        return sqDAO.addSecurityQuestion(sq);
    }

    // ---------------- Get questions for user ----------------
    public List<SecurityQuestion> getSecurityQuestions(int userId) {
        return sqDAO.getQuestionsByUserId(userId);
    }

    // ---------------- Verify answer ----------------
    public boolean verifyAnswer(SecurityQuestion sq, String answer) {
        return HashUtil.verifyHash(answer, sq.getAnswer());
    }
}
