package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.model.Classroom;
import com.example.dangkyhoc.model.Course;
import com.example.dangkyhoc.tableModel.AccountModel;
import com.example.dangkyhoc.tableModel.ClassModel;
import com.example.dangkyhoc.tableModel.CourseModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ClassController extends MenuController{

    @FXML
    TextField class_name;
    @FXML
    TextField class_max;
    @FXML
    ComboBox<Course> class_course;

    ObservableList<Course> course_list = FXCollections.observableArrayList();

    @FXML
    TableView<ClassModel> class_table;
    @FXML
    TableColumn<ClassModel,String> class_col_name;
    @FXML
    TableColumn<ClassModel,String> class_col_max;
    @FXML
    TableColumn<ClassModel,String> class_col_current;
    @FXML
    TableColumn<ClassModel,String> class_col_status;
    @FXML
    TableColumn<ClassModel,String> class_col_course;


    ObservableList<ClassModel> classModelObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        if (class_table==null){
            populateCourses();
        }else{
            populateClasses();
            setUpSearch();
        }

    }

    private void populateCourses(){
        ResultSet courses = new Course().all();
        try{
            while (courses.next()){
                Course course = new Course();
                course.setAttribute(courses);
                course_list.add(course);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        class_course.setItems(course_list);
    }

    public void createClass(ActionEvent event) {

        if (class_name.getText().isBlank() || class_max.getText().isBlank()){
            Helper.showMessage("Lỗi", "Không được để trống các trường");
            return;
        }

        if (!Helper.validateNumber(class_max.getText())){
            Helper.showMessage("Lỗi", "Số lượng học viên tối đa sai định dạng");
            return;
        }

        Course selectedCourse = class_course.getSelectionModel().getSelectedItem();

        if (selectedCourse ==null){
            Helper.showMessage("Lỗi", "Chưa chọn trường môn học");
            return;
        }

        Classroom classroom = new Classroom(class_name.getText(),Integer.parseInt(class_max.getText()),selectedCourse.getId());

        if (!classroom.save()){
            Helper.showMessage("Lỗi","Thêm mới lớp học không thành công");
            return;
        }

        Helper.showMessage("Thành công","Thêm mới lớp học thành công");

    }

    private void populateClasses(){
        initCols();
        initEditCols();
        initData();
    }

    private void initCols(){
        class_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        class_col_max.setCellValueFactory(new PropertyValueFactory<>("max"));
        class_col_current.setCellValueFactory(new PropertyValueFactory<>("currentStudent"));
        class_col_status.setCellValueFactory(new PropertyValueFactory<>("isClose"));
        class_col_course.setCellValueFactory(new PropertyValueFactory<>("courseName"));

    }

    private void initEditCols(){
        class_col_name.setCellFactory(TextFieldTableCell.forTableColumn());
        class_col_name.setOnEditCommit(e->{
            ClassModel row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setName(e.getNewValue());
            row.updateClass();
        });

        class_col_max.setCellFactory(TextFieldTableCell.forTableColumn());
        class_col_max.setOnEditCommit(e->{
            ClassModel row = e.getTableView().getItems().get(e.getTablePosition().getRow());
            row.setMax(e.getNewValue());
            row.updateClass();
        });

        class_table.setEditable(true);
    }

    private void initData(){
        classModelObservableList = FXCollections.observableArrayList();
        ResultSet classroom = new Classroom().allWithCourse();
        try{
            while (classroom.next()){
                ClassModel classModel = new ClassModel();
                classModel.setAttributeWithCourse(classroom);
                classModelObservableList.add(classModel);
            }
            class_table.setItems(classModelObservableList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteRow(ActionEvent event) {
        int selectedId = class_table.getSelectionModel().getSelectedIndex();
        if (selectedId==-1)
            return;

        ClassModel row = class_table.getItems().get(selectedId);

        if (!row.deleteClass()) {
            Helper.showMessage("Lỗi","Xóa thất bại");
            return;
        }

        class_table.getItems().remove(selectedId);
    }


    public void openClass(ActionEvent event) {
        int selectedId = class_table.getSelectionModel().getSelectedIndex();
        if (selectedId==-1)
            return;

        ClassModel row = class_table.getItems().get(selectedId);
        row.setIsCloseBool(false);
        if (!row.updateClass()) {
            Helper.showMessage("Lỗi","Mở lớp thất bại");
            return;
        }

        class_table.setItems(classModelObservableList);
    }

    public void closeClass(ActionEvent event) {
        int selectedId = class_table.getSelectionModel().getSelectedIndex();
        if (selectedId==-1)
            return;

        ClassModel row = class_table.getItems().get(selectedId);
        row.setIsCloseBool(true);
        if (!row.updateClass()) {
            Helper.showMessage("Lỗi","Đóng lớp thất bại");
            return;
        }

        class_table.setItems(classModelObservableList);
    }

    @FXML
    TextField search_bar;


    FilteredList<ClassModel> filteredList;
    private void setUpSearch(){
        filteredList = new FilteredList<>(classModelObservableList,b->true);
        search_bar.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate(searchModel ->{

                if (newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }

                String searchedKey= newValue.toLowerCase();

                if (searchModel.getName().toLowerCase().indexOf(searchedKey) > -1){
                    return true;
                }

                if (searchModel.getIsClose().toLowerCase().indexOf(searchedKey) > -1){
                    return true;
                }

                if (searchModel.getMax().toLowerCase().indexOf(searchedKey) > -1){
                    return true;
                }

                if (searchModel.getCurrentStudent().toLowerCase().indexOf(searchedKey) > -1){
                    return true;
                }

                if (searchModel.getCourseName().toLowerCase().indexOf(searchedKey) > -1){
                    return true;
                }

                return false;

            });

            SortedList<ClassModel> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(class_table.comparatorProperty());
            class_table.setItems(sortedList);
        });
    }
}
