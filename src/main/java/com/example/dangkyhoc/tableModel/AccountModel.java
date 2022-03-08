package com.example.dangkyhoc.tableModel;

import com.example.dangkyhoc.model.Account;
import com.example.dangkyhoc.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountModel {
    Integer id;
    String fullName,role,username,password;
    String credit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public AccountModel() {
    }

    public AccountModel(Integer id, String fullName, String role, String username, String password, String credit) {
        this.id = id;
        this.fullName = fullName;
        this.role = role;
        this.username = username;
        this.password = password;
        this.credit = credit;
    }

    public void setAttribute(ResultSet resultSet) {
        try {
            setFullName(resultSet.getString("full_name"));
            setCredit(String.valueOf(resultSet.getInt("credit")) );
            setUsername(resultSet.getString("username"));
            setRole(resultSet.getString("role"));
            id = resultSet.getInt("id");
            password = resultSet.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account getOb() {
        Account account = new Account(id,username,password,role,fullName,Integer.parseInt(credit));
        return account;
    }

    public boolean updateCourse() {
        return getOb().save();
    }

    public boolean deleteCourse() {
        return getOb().delete();
    }
}
