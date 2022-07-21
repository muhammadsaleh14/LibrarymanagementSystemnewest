package com.example.librarymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class LibrarianController implements Initializable {
    @FXML private Button exit;
//    @FXML private TableView<Books> table;
//    @FXML private TableColumn<Books, Integer> status;
//    @FXML private TableColumn<Books, String> title;
//    @FXML private TableColumn<Books, String> genre;
//    @FXML private TableColumn<Books, String> author;
//    @FXML private TableColumn<Books, String> description;
//    @FXML private TextField new_username;
//    @FXML private Label username_already_exists;
//    @FXML private TextField new_password;
//    @FXML private TextField confirm_password;
    @FXML private Label error;
    @FXML
    private ImageView imageView;



    public void exit(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    public void editAccount(ActionEvent event)  {
        try {
            new Methods().switchScenes(event, "editAccount.fxml");
        } catch (IOException e) {
            error.setText("Error occured");
            e.printStackTrace();
        }
    }
    public void pendingUsers(ActionEvent event)  {
        try {
            new Methods().switchScenes(event,"pending_users.fxml");
        } catch (IOException e) {
            error.setText("Error occured");
            e.printStackTrace();
        }

    }


    public void editBook(ActionEvent event){
        try {
            new Methods().switchScenes(event,"editBook.fxml.");
        } catch (IOException e) {
            error.setText("Error occured");
        e.printStackTrace();
        }
    }
    public void goToUsers(ActionEvent event){
        try {
            new Methods().switchScenes(event,"adminUsers.fxml");
        } catch (IOException e) {
            error.setText("Error occured");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageView.setImage(new Image("image.jpg"));
    }
}
