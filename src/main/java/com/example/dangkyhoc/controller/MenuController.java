package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.Store;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    MenuBar home_menu;

    @FXML
    MenuItem logout_item;

    @FXML
    MenuItem change_password_item;

    @FXML
    MenuItem course_list_item;

    @FXML
    MenuItem create_course_item;

    @FXML
    MenuItem create_class_item;

    @FXML
    MenuItem class_list_item;

    @FXML
    MenuItem create_student_item;

    @FXML
    MenuItem student_list_item;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMenuEvent();
    }

    protected void setMenuEvent(){
        logout_item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Store.account = null;
                Helper.changeScene(home_menu,"login.fxml");
            }
        });

        setMenuItemEvent(change_password_item,"change_password.fxml");
        setMenuItemEvent(course_list_item,"course_list.fxml");
        setMenuItemEvent(create_course_item,"create_course.fxml");
        setMenuItemEvent(class_list_item,"class_list.fxml");
        setMenuItemEvent(create_class_item,"create_class.fxml");
        setMenuItemEvent(student_list_item,"student_list.fxml");
        setMenuItemEvent(create_student_item,"create_student.fxml");
    }

    private void setMenuItemEvent(MenuItem menuItem, String screen) {
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (screen.isBlank()){
                        Helper.showMessage("lỗi","Chưa code màn hình này");
                    }else{
                        Helper.changeScene(home_menu,screen);
                    }
                }
            });
    }
}
