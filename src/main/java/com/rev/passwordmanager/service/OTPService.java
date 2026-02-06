package com.rev.passwordmanager.service;

import com.rev.passwordmanager.dao.VerificationCodeDAO;
import com.rev.passwordmanager.model.VerificationCode;
import com.rev.passwordmanager.Util.OTPUtil;

import java.time.LocalDateTime;

public class OTPService {

    private final VerificationCodeDAO vcDAO = new VerificationCodeDAO();
    private static final int EXPIRY_MINUTES = 5;

    // ---------------- Generate OTP ----------------
    public String generateOTP(int userId) {
        String code = OTPUtil.generateOTP();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(EXPIRY_MINUTES);
        VerificationCode vc = new VerificationCode(userId, code, expiry);
        vcDAO.saveVerificationCode(vc);
        return code;
    }

    // ---------------- Verify OTP ----------------
    public boolean verifyOTP(int userId, String enteredOtp) {
        VerificationCode latest = vcDAO.getLatestCodeByUserId(userId);
        if (latest == null) return false;
        if (LocalDateTime.now().isAfter(latest.getExpiry())) return false;
        return latest.getCode().equals(enteredOtp);
    }
}
