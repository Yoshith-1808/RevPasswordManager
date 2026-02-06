package com.rev.passwordmanager.Util;

import java.security.MessageDigest;

public class HashUtil {

    public static String hashString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hashing failed: " + e.getMessage());
        }
    }

    public static boolean verifyHash(String raw, String hashed) {
        return hashString(raw).equals(hashed);
    }
}
