package com.rev.passwordmanager.Util;

import java.util.Random;

public class OTPUtil {

    private static final int OTP_LENGTH = 6;
    private static final Random random = new Random();

    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) otp.append(random.nextInt(10));
        return otp.toString();
    }
}
