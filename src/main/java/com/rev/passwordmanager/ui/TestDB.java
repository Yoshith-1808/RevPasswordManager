package com.rev.passwordmanager.ui;

import com.rev.passwordmanager.Util.DBConnection;

public class TestDB {
    public static void main(String[] args) {
        try {
            DBConnection.getConnection();
            System.out.println("DATABASE CONNECTED");
        } catch (Exception e) {
            System.out.println("Failed to connect to database:");
            e.printStackTrace();
        }
    }
}
