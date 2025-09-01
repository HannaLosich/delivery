package com.solvd.delivery.mysqlImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AMySQLDB {

    // JDBC URL with SSL disabled and proper timezone handling
    private static final String URL = "jdbc:mysql://localhost:3306/delivery_service?allowPublicKeyRetrieval=true&useSSL=false";

    // Database credentials
    private static final String USER = "root";
    private static final String PASSWORD = "newpassword123";

    static {
        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL JDBC Driver registered successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    // Now public so Main/App can use it
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
