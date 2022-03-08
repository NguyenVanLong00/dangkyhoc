package com.example.dangkyhoc;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {
    public static int screenWidth=1080;
    public static int screenHeight = 768;

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String encoded = no.toString(16);
            while (encoded.length() < 32) {
                encoded = "0" + encoded;
            }
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeScene(Node node, String fxmlFile){
        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader(Helper.class.getResource(fxmlFile));
            root = loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(root);
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root,Helper.screenWidth,Helper.screenHeight));
        stage.show();
    }

    public static void showMessage(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }

    public static boolean validateNumber(String text)
    {
        return text.matches("[0-9]*");
    }

    final public static String file = "src/main/resources/com/example/dangkyhoc/account.txt";

    public static void writeToFile(String username,String password) throws IOException {
        String str = username+"\n"+password;
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(str);

        writer.close();
    }
}
