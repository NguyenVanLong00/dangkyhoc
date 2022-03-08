package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.Store;
import com.example.dangkyhoc.model.Account;
import com.example.dangkyhoc.tableModel.AccountModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class StudentAccountController extends studentMenuController {

    @FXML
    PasswordField current_password;
    @FXML
    PasswordField new_password;
    @FXML
    PasswordField confirm_password;

    public void ChangePassword(ActionEvent event) {

        if (new_password.getText().isBlank() || current_password.getText().isBlank() || confirm_password.getText().isBlank()){
            Helper.showMessage("Lỗi","Các trường không được bỏ trống");
            return;
        }

        if (! new_password.getText().equals(confirm_password.getText())){
            Helper.showMessage("Lỗi","Mật khẩu không trùng nhau!");
            return;
        }

        System.out.println(current_password.getText());
        if (!Store.account.checkPassword(current_password.getText())){
            Helper.showMessage("Lỗi","Mật khẫu cũ không đúng!");
            return;
        }

        Store.account.setPassword(new_password.getText());
        if (!Store.account.save()){
            Helper.showMessage("Lỗi","Đổi mật khẩu thất bại! xảy ra lỗi trong quá trình đổi mật khẩu");
            return;
        }

        Helper.showMessage("Thành công","Đổi mật khẩu thành công");
    }
}
