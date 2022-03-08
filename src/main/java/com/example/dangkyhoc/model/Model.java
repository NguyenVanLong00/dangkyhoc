package com.example.dangkyhoc.model;

import com.example.dangkyhoc.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {
    protected Connection connection;
    protected Statement statement;
    protected String table ="";
    protected Integer id =null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Model(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        connection = databaseConnection.getConnection();
    }

    public ResultSet all(){
        return where("1=1");
    }

    public ResultSet where(String condition) {
        try{
            String query = "SELECT * FROM "+table+" where " + condition;

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void refresh() {
        find("id=" + id.toString());
    }

    public boolean delete() {
        String query = "DELETE FROM `"+table+"` WHERE (`id` = '"+id.toString()+"');";
        return executeUpdate(query);
    }


    public void find(String condition) {
        String query = "SELECT * FROM " + table + " where " + condition;
        try {
            ResultSet resultSet = executeQuery(query);
            setAttribute(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAttribute(ResultSet resultSet) {
    }

    public boolean executeUpdate(String query){
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery(String query){
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.isBeforeFirst()) {
                return null;
            } else {
                return resultSet;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
