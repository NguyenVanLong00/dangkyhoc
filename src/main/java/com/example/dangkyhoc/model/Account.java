package com.example.dangkyhoc.model;


import com.example.dangkyhoc.Helper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account extends Model {

    public enum roles {
        ADMIN("admin"),
        USER("user");

        private final String text;

        roles(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public Account() {
        super();
        this.table = "account";
    }

    public Account(String fullName, String username, String password) {
        super();
        this.table = "account";

        this.fullName = fullName;
        this.username = username;
        setPassword(password);
        this.role = "user";
    }

    private String username;
    private String password;
    private String role;

    public Account(Integer id, String username, String password, String role, String fullName, Integer credit) {
        super();
        this.table = "account";
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.credit = credit;
    }

    public String getPassword() {
        return password;
    }

    private String fullName;
    private Integer credit;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = Helper.getMd5(password);
    }

    public void setRawPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        System.out.println("old: " + this.password);
        System.out.println("new: " + Helper.getMd5(password));
        return this.password.equals(Helper.getMd5(password));
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCredit() {
        return credit;
    }

    public boolean setCredit(int credit) {
        if (credit >= 0) {
            this.credit = credit;
            return true;
        }
        return false;
    }

    public boolean save() {
        String query;

        if (id != null) {
            query = "UPDATE " + table + " SET full_name='" + fullName + "', role='" + role + "',username='" + username + "',password='" + password + "',credit=" + credit.toString() + " WHERE id=" + id.toString();
        } else {
            query = "INSERT INTO `dangkyhoc`.`account` (`full_name`,`role`, `username`, `password`) VALUES ('" + fullName + "', '" + role + "','" + username + "', '" + password + "');";
        }

        return executeUpdate(query);
    }

    public boolean login() {
        String condition = "username ='" + username + "' and password ='" + password + "'";
        String query = "SELECT * FROM " + table + " where " + condition;
        System.out.println("query: " + query);

        ResultSet resultSet = executeQuery(query);
        if (resultSet == null) {
            return false;
        }

        setAttribute(resultSet);
        return true;
    }

    public void setAttribute(ResultSet resultSet) {
        try {
            resultSet.next();

            setFullName(resultSet.getString("full_name"));
            setCredit(resultSet.getInt("credit"));
            setUsername(resultSet.getString("username"));
            setRole(resultSet.getString("role"));
            id = resultSet.getInt("id");
            password = resultSet.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
