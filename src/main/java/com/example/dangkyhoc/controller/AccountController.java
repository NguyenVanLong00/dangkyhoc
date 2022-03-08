package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.Store;
import com.example.dangkyhoc.model.Account;
import com.example.dangkyhoc.model.Classroom;
import com.example.dangkyhoc.model.Course;
import com.example.dangkyhoc.tableModel.AccountModel;
import com.example.dangkyhoc.tableModel.CourseModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.InputMethodEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.ResourceBundle;

public class AccountController extends MenuController {

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


    @FXML
    TextField student_fullName;
    @FXML
    TextField student_username;
    @FXML
    TextField student_password;


    public void createStudent(ActionEvent event) {
        if (student_fullName.getText().isBlank() || student_username.getText().isBlank()){
            Helper.showMessage("Lỗi","Không được để trống các trường");
            return;
        }

        String password = student_password.getText();
        if (password.isBlank()){
            password = "123456";
        }

        Account account = new Account(student_fullName.getText(),student_username.getText(),password);

        if (!account.save()){
            Helper.showMessage("Lỗi","Thêm học viên thất bại");
        }

        Helper.showMessage("Thành công","Thêm học viên thành công");
    }


    @FXML
    TableView<AccountModel> student_table;

    @FXML
    TableColumn<AccountModel,String> student_col_fullName;

    @FXML
    TableColumn<AccountModel,String> student_col_username;

    @FXML
    TableColumn<AccountModel, String> student_col_credit;

    ObservableList<AccountModel> accountModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMenuEvent();
        if (student_table!=null){
            populateTableData();
            setUpSearch();
        }
    }

    private void populateTableData(){
        initCols();
        initEditCols();
        initData();
    }

    private void initCols(){
        student_col_fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        student_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        student_col_credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
    }

    private void initEditCols(){
        student_col_fullName.setCellFactory(TextFieldTableCell.forTableColumn());
        student_col_fullName.setOnEditCommit(e->{
            AccountModel row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setFullName(e.getNewValue());
            row.updateCourse();
        });

        student_col_username.setCellFactory(TextFieldTableCell.forTableColumn());
        student_col_username.setOnEditCommit(e->{
            AccountModel row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setUsername(e.getNewValue());
            row.updateCourse();
        });

        student_col_credit.setCellFactory(TextFieldTableCell.forTableColumn());
        student_col_credit.setOnEditCommit(e->{
            AccountModel row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setCredit(e.getNewValue());
            row.updateCourse();
        });

        student_table.setEditable(true);
    }

    private void initData(){
        ResultSet accounts = new Account().all();
        try{
            while (accounts.next()){
                AccountModel account = new AccountModel();
                account.setAttribute(accounts);
                accountModelObservableList.add(account);
            }
            student_table.setItems(accountModelObservableList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteRow(ActionEvent event) {
        int selectedId = student_table.getSelectionModel().getSelectedIndex();
        AccountModel row = student_table.getItems().get(selectedId);

        if (!row.deleteCourse()) {
            Helper.showMessage("Lỗi","Xóa thất bại");
            return;
        }

        student_table.getItems().remove(selectedId);
    }


    @FXML
    TextField search_bar;


    FilteredList<AccountModel> filteredList;
    private void setUpSearch(){
        filteredList = new FilteredList<>(accountModelObservableList,b->true);
        search_bar.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(searchModel ->{

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchedKey= newValue.toLowerCase();
                if (searchModel.getUsername().toLowerCase().indexOf(searchedKey) > -1){
                    return true;
                }

                if (searchModel.getFullName().toLowerCase().indexOf(searchedKey) > -1){
                    return true;
                }

                return false;

            });

            SortedList<AccountModel> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(student_table.comparatorProperty());
            student_table.setItems(sortedList);
        });
    }
}
