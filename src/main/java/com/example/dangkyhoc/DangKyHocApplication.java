package com.example.dangkyhoc;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DangKyHocApplication extends javafx.application.Application {

    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DangKyHocApplication.class.getResource("login.fxml"));

        scene = new Scene(fxmlLoader.load(),1080 ,768);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}