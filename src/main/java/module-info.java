module com.example.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires controlsfx;
//    requires org.controlsfx.controls;


    opens com.example.librarymanagementsystem to javafx.fxml;
    exports com.example.librarymanagementsystem;

}