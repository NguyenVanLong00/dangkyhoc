package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.tableModel.CourseModel;
import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.model.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class CourseController extends MenuController{

    @FXML
    TextField course_name;

    @FXML
    TextArea course_description;

    @FXML
    TextField course_credit;

    @FXML
    TextField course_credit_condition;

    public void createCourse(ActionEvent event) {

        if (course_name.getText().isBlank() || course_credit.getText().isBlank() || course_credit_condition.getText().isBlank()){
            Helper.showMessage("Lỗi","Không được để trống các trường");
            return;
        }

        if (!Helper.validateNumber(course_credit.getText())){
            Helper.showMessage("Lỗi","Tín chỉ sai định dạng");
            return;
        }
        if (!Helper.validateNumber(course_credit_condition.getText())){
            Helper.showMessage("Lỗi","Tín chỉ tiên quyết sai định dạng");
            return;
        }

        Course course = new Course(course_name.getText(),course_description.getText(),Integer.parseInt(course_credit.getText()),Integer.parseInt(course_credit_condition.getText()));
        if (!course.save()){
            Helper.showMessage("Lỗi","Thêm môn học thất bại");
            return;
        }

        Helper.showMessage("Thành công","Thêm môn học thành công");

    }

    @FXML
    TableView<CourseModel> course_table;

    @FXML
    TableColumn<CourseModel,String> course_col_name;

    @FXML
    TableColumn<CourseModel,String> course_col_description;

    @FXML
    TableColumn<CourseModel,String> course_col_credit;

    @FXML
    TableColumn<CourseModel,String> course_col_credit_condition;

    ObservableList<CourseModel> courseObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMenuEvent();
        if (course_table!=null){
            populateTableData();
        }
    }

    private void populateTableData(){
        initCols();
        initEditCols();
        initData();
    }

    private void initCols(){
        course_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        course_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        course_col_credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        course_col_credit_condition.setCellValueFactory(new PropertyValueFactory<>("creditCondition"));
    }

    private void initEditCols(){
        course_col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        course_col_name.setOnEditCommit(e->{
            CourseModel row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setName(e.getNewValue());
            row.updateCourse();
        });

        course_col_description.setCellFactory(TextFieldTableCell.forTableColumn());
        course_col_description.setOnEditCommit(e->{
            CourseModel row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setDescription(e.getNewValue());
            row.updateCourse();
        });

        course_col_credit.setCellFactory(TextFieldTableCell.forTableColumn());
        course_col_credit.setOnEditCommit(e->{
            CourseModel row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setCredit(e.getNewValue());
            row.updateCourse();
        });

        course_table.setEditable(true);
    }

    private void initData(){
        ResultSet courses = new Course().all();
        try{
            while (courses.next()){
                CourseModel course = new CourseModel();
                course.setAttribute(courses);
                courseObservableList.add(course);
            }
            course_table.setItems(courseObservableList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteRow(ActionEvent event) {
        int selectedId = course_table.getSelectionModel().getSelectedIndex();
        CourseModel row = course_table.getItems().get(selectedId);

        if (!row.deleteCourse()) {
            Helper.showMessage("Lỗi","Xóa thất bại");
            return;
        }

        course_table.getItems().remove(selectedId);
    }
}
