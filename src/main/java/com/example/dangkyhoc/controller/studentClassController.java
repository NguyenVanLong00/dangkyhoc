package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.Store;
import com.example.dangkyhoc.model.Classroom;
import com.example.dangkyhoc.model.Course;
import com.example.dangkyhoc.tableModel.ClassModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class studentClassController extends studentMenuController {

    @FXML
    TableView<ClassModel> class_table;
    @FXML
    TableColumn<ClassModel, String> class_col_name;
    @FXML
    TableColumn<ClassModel, String> class_col_max;
    @FXML
    TableColumn<ClassModel, String> class_col_course;
    @FXML
    TableColumn<ClassModel, String> class_col_status;
    @FXML
    TableColumn<ClassModel, Integer> class_col_credit;

    @FXML
    Button class_list_view;


    ObservableList<ClassModel> classModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        initCols();

        if (class_list_view == null) {
            initData();
        } else {
            initMyClassData();
        }
        setUpSearch();
    }

    private void initCols() {
        class_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        class_col_max.setCellValueFactory(new PropertyValueFactory<>("max"));
        class_col_course.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        class_col_status.setCellValueFactory(new PropertyValueFactory<>("isClose"));
        class_col_course.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        class_col_credit.setCellValueFactory(new PropertyValueFactory<>("credit"));

    }

    private void initData() {
        classModelObservableList = FXCollections.observableArrayList();
        ResultSet classroom = new Classroom().allOpenWithCourse();
        try {
            while (classroom.next()) {
                ClassModel classModel = new ClassModel();
                classModel.setAttributeWithCourse(classroom);
                classModelObservableList.add(classModel);
            }
            class_table.setItems(classModelObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMyClassData() {
        classModelObservableList = FXCollections.observableArrayList();
        ResultSet classroom = new Classroom().accountClassWithCourse(Store.account.getId());
        if (classroom==null)
        {
            return;
        }
        try {
            while (classroom.next()) {
                ClassModel classModel = new ClassModel();
                classModel.setAttributeWithCourse(classroom);
                classModelObservableList.add(classModel);
            }
            class_table.setItems(classModelObservableList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    TextField search_bar;


    FilteredList<ClassModel> filteredList;

    private void setUpSearch() {
        filteredList = new FilteredList<>(classModelObservableList, b -> true);
        search_bar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(searchModel -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }

                String searchedKey = newValue.toLowerCase();

                if (searchModel.getCourseName().toLowerCase().indexOf(searchedKey) > -1) {
                    return true;
                }

                if (searchModel.getName().toLowerCase().indexOf(searchedKey) > -1) {
                    return true;
                }

                if (searchModel.getMax().toLowerCase().indexOf(searchedKey) > -1) {
                    return true;
                }
                if (searchModel.getIsClose().toLowerCase().indexOf(searchedKey) > -1) {
                    return true;
                }

                return false;

            });

            SortedList<ClassModel> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(class_table.comparatorProperty());
            class_table.setItems(sortedList);
        });
    }

    public void registerClass(ActionEvent event) {
        int selectedId = class_table.getSelectionModel().getSelectedIndex();
        if (selectedId==-1)
            return;

        ClassModel row = class_table.getItems().get(selectedId);


        if (row.isInClass()) {
            Helper.showMessage("Thất bại", "Bạn đã ở trong lớp này");
            return;
        }

        if (!row.registerClass()) {
            Helper.showMessage("Lỗi", "Đăng ký lớp thất bại");
            return;
        }
        Helper.showMessage("Thành công", "Đăng ký lớp thành công");
        class_table.setItems(classModelObservableList);
    }

    public void cancelClass(ActionEvent event) {
        int selectedId = class_table.getSelectionModel().getSelectedIndex();
        if (selectedId==-1)
            return;

        ClassModel row = class_table.getItems().get(selectedId);

        if (!row.isInClass()) {
            Helper.showMessage("Thất bại", "Bạn không ở trong lớp này");
            return;
        }

        if (!row.cancelClass()) {
            Helper.showMessage("Lỗi", "Hủy lớp thất bại");
            return;
        }

        Helper.showMessage("Thành công", "Hủy đăng ký lớp thành công");
        class_table.setItems(classModelObservableList);
    }
}
