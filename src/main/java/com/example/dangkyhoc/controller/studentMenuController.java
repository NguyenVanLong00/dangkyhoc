package com.example.dangkyhoc.controller;

import com.example.dangkyhoc.Helper;
import com.example.dangkyhoc.Store;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class studentMenuController implements Initializable {

    @FXML
    MenuBar home_menu;

    @FXML
    MenuItem logout_item;

    @FXML
    MenuItem change_password_item;

    @FXML
    MenuItem student_class_list_item;

    @FXML
    MenuItem register_class_item;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMenuEvent();
    }

    protected void setMenuEvent(){
        logout_item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Store.account = null;
                try {
                    Helper.writeToFile("","");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Helper.changeScene(home_menu,"login.fxml");
            }
        });

        setMenuItemEvent(change_password_item,"student_change_password.fxml");
        setMenuItemEvent(student_class_list_item,"student_class_list.fxml");
        setMenuItemEvent(register_class_item,"register_class.fxml");
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
