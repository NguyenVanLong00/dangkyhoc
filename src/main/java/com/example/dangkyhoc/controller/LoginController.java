package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.Store;
import com.example.dangkyhoc.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Text error_message;

    public void login(ActionEvent actionEvent) throws IOException {
        Account account = new Account();
        account.setUsername(username.getText());
        account.setPassword(password.getText());
        if (account.login()){
            System.out.println("Login success");
            Store.account = account;

            System.out.println("role "+account.getRole());

            if (account.getRole().equals("admin")){
                Helper.changeScene(actionEvent,"course_list.fxml");
                return;
            }
            Helper.changeScene(actionEvent,"student_class_list.fxml");

        }else{
            error_message.setVisible(true);
            System.out.println("Login failed");
        }
    }
}