package com.example.dangkyhoc.model;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Statement;
import java.util.Map;

public class Account extends Model{

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


    private String username;
    private String password;
    private String role;
    private String fullName;
    private int credit;
    private String id;
    private String query;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public void setPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        this.password = new String(messageDigest.digest());
    }

    public boolean checkPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        return this.password == new String(messageDigest.digest());
    }

    public String getRole() {
        return role;
    }

    public boolean setRole(String role) {
        if (role == roles.ADMIN.toString() || role == roles.USER.toString()){
            this.role = role;
            return true;
        }
        return false;
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
        if (credit >0) {
            this.credit = credit;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public void refresh(){
        String query = "SELECT * FROM account where id="+this.id.toString();

    }

}
