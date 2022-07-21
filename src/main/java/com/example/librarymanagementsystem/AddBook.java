package com.example.librarymanagementsystem;

import com.example.librarymanagementsystem.DBUtil;
import com.example.librarymanagementsystem.Methods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class AddBook implements Initializable {

public  String urlOfImage;
    ObservableList<String> searchList = FXCollections.observableArrayList();
    TextBox var;
    @FXML
    private TextField author;
    @FXML
    private ComboBox<String> selectcategory;
    @FXML
    private ComboBox<String> selectbooktype;
    @FXML
    private ComboBox<String> selectauthor;
    @FXML
    private TextField title;
    @FXML
    private ListView<String> searchlistview;
    @FXML
    private  TextField category;
    @FXML
    private TextField language;
    @FXML
    private Label selection;
    @FXML
    private TextField publisher;
    @FXML
   private Label error;
    @FXML
    private ImageView imageView;
    public AddBook() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        searchList.addAll("Fresh Arrival","Reference");
        selectbooktype.setItems(searchList);
    }

    public void back(ActionEvent event){
        try {
            new Methods().switchScenes(event,"editBook.fxml");
        } catch (IOException e) {
            Methods.setText(error,"Error Occured");
            e.printStackTrace();
        }
    }

    public void search(KeyEvent event)  {
        try {
            TextField textField = (TextField) event.getSource();
            if (textField == author) {
                keyTyped(author,selection,searchlistview);
            } else if (textField == title) {
                keyTyped(title,selection,searchlistview);
            } else if (textField == category) {
                keyTyped(category,selection,searchlistview);
            } else if (textField == language) {
                keyTyped(language,selection,searchlistview);
            } else if (textField == publisher){
                keyTyped(publisher,selection,searchlistview);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void callSelect(){
        select(searchlistview);
    }

    public void  keyTyped(TextField textField,Label label,ListView<String> listView) {
        try {
            ObservableList<String> list = FXCollections.observableArrayList();
            String searchString = textField.getText().trim();
            searchString = searchString.replaceAll("","%");
            searchString = searchString.replaceAll(" ","");
            if (textField == author) {
                list = DBUtil.searchBooksList("author", searchString, "SELECT DISTINCT author FROM newbooks WHERE author LIKE ? ORDER BY author");
                var = TextBox.AUTHOR;
                label.setText("Search Author");
            } else if (textField == title) {
                list = DBUtil.searchBooksList("Title", searchString, "SELECT DISTINCT Title FROM newbooks WHERE Title LIKE ? ORDER BY Title");
                var = TextBox.TITLE;
                label.setText("Search Title");
            } else if (textField == category) {
                list = DBUtil.searchBooksList("category", searchString, "SELECT DISTINCT category FROM newbooks WHERE category LIKE ? ORDER BY category");
                var = TextBox.CATEGORY;
                label.setText("Search Category");
            } else if (textField == language) {
                list = DBUtil.searchBooksList("languagee", searchString, "SELECT DISTINCT languagee FROM newbooks WHERE languagee LIKE ? ORDER BY languagee");
                var = TextBox.LANGUAGE;
                label.setText("Search Language");
            }
            listView.setItems(list);
        }catch (Exception e){e.printStackTrace();}

    }

    public void select(ListView<String> listView){
        try {
            if(var == TextBox.AUTHOR){
                author.setText(listView.getSelectionModel().getSelectedItem());
            }
            else if(var == TextBox.TITLE){
                title.setText(listView.getSelectionModel().getSelectedItem());
            }
            else if (var == TextBox.CATEGORY){
                category.setText(listView.getSelectionModel().getSelectedItem());
            }
            else if(var == TextBox.LANGUAGE){
                language.setText(listView.getSelectionModel().getSelectedItem());
            }

        }
        catch (Exception ev) {
            ev.printStackTrace();
        }
    }

    public void submit(){
        try {
            java.util.Date dt = new java.util.Date();
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentTime = sdf.format(dt);
            boolean checkNull = true;
        String[] arr = {title.getText(),author.getText(),category.getText(),language.getText(),
                           selectbooktype.getValue(),currentTime,urlOfImage};
        for(String i : arr){
            if (i == null || i.equals("")) {
                checkNull = false;
                break;
            }
        }

        if(checkNull == false){
            error.setText("Fill out all fields");
        }
        else{
            if(DBUtil.AddNewBook(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6])){
                Methods.setText(error,"Book has been added");
            }
            else {
                error.setText("Book already exists");
            }
        }
        } catch (SQLException e) {
            error.setText("Error occured");
        }
    }

    public String addImage(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
//        Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG JPG JPEG", "*.jpg", "*.png", "*.jpeg"));
            File file = new File(String.valueOf(fileChooser.showOpenDialog(null)));
            Image image = new Image(file.getAbsolutePath());
            urlOfImage = image.getUrl();
            imageView.setPreserveRatio(false);
            imageView.setImage(image);
            return image.getUrl();
        } catch (Exception e){
            Methods.setText(error,"Wrong File / Error");
        }
        return null;
    }
    enum TextBox {
        TITLE,
        AUTHOR,
        CATEGORY,
        LANGUAGE,
    }
}

