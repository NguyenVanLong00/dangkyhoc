module com.example.dangkyhoc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    exports com.example.dangkyhoc;
    exports com.example.dangkyhoc.controller;
    opens com.example.dangkyhoc.controller to javafx.fxml;
}