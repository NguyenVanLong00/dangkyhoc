package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.Store;
import com.example.dangkyhoc.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Text error_message;



    public void login(ActionEvent actionEvent) throws IOException {
        checkLogin(username.getText(),Helper.getMd5(password.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void loginFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(Helper.file));
        String username = reader.readLine();
        String password = reader.readLine();
        reader.close();
        checkLogin(username,password);
    }

    public void checkLogin(String username,String password) throws IOException {
        Account account = new Account();
        account.setUsername(username);
        account.setRawPassword(password);
        if (account.login()) {
            System.out.println("Login success");
            Store.account = account;

            Helper.writeToFile(account.getUsername(),account.getPassword());

            System.out.println("role " + account.getRole());

            if (account.getRole().equals("admin")) {
                Helper.changeScene(this.username, "course_list.fxml");
                return;
            }
            Helper.changeScene(this.username, "student_class_list.fxml");

        } else {
            error_message.setVisible(true);
            System.out.println("Login failed");
        }
    }
}