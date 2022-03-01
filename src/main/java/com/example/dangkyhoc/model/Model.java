package com.example.dangkyhoc.model;

import com.example.dangkyhoc.DatabaseConnection;

public class Model {
    private DatabaseConnection connection;

    public Model(){
        connection = new DatabaseConnection();
        connection.getConnection();
    }
}
