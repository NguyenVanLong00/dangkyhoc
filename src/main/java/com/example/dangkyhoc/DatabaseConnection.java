package com.example.dangkyhoc;


import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    protected Connection database;

    public Connection getConnection() {
        String databaseName = "dangkyhoc";
        String databaseUser = "root";
        String databasePassword = "123456";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            database = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        return database;
    }

    public DatabaseConnection() {
        getConnection();
    }
}
