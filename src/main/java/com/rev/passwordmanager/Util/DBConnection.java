package com.rev.passwordmanager.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/revpassword_manager?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";          // change if your MySQL user is different
    private static final String PASS = "Yoshith@18";   // change to your MySQL password

    static {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
    }

    // Get DB connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
