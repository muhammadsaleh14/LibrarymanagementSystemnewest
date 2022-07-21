package com.example.librarymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class EditBook implements Initializable {
    public static Books bookInEditBook ;
    ObservableList<Books> list = FXCollections.observableArrayList();
    @FXML
    ImageView imageView;
    @FXML
    Label noImageAvailible;
    @FXML
    private TableColumn<Books,String> author;
    @FXML
    private TableColumn<Books, String> bookStatus;
    @FXML
    private TableColumn<Books,String> category;
    @FXML
    private TableColumn<Books,String> language;
    @FXML
    private TableView<Books> table;
    @FXML
    private TableColumn<Books,String> copiesOwned;
    @FXML
    private TableColumn<Books,ImageView> image;
    @FXML
    private TableColumn<Books,String> title;
    @FXML
    private TextField tfsearch;
    //imageColumn.setCellFactory(param -> new ImageTableCell<>());
    //    TableColumn<Fish, Image> imageColumn = new TableColumn<>("Picture");
//imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
    @FXML
    private Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DBUtil.setAvailibilityForFreshArrival();
        } catch (SQLException e) {
            Methods.setText(error,"Error occured");
        }
        image.setPrefWidth(50);//same as image view
            table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                bookInEditBook =  newValue;
            });
            try {
            //initializing table

            DBUtil.reloadTableView(table);
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        language.setCellValueFactory(new PropertyValueFactory<>("languagee"));
        bookStatus.setCellValueFactory(new PropertyValueFactory<>("bookStatus"));
        copiesOwned.setCellValueFactory(new PropertyValueFactory<>("copiesOwned"));
        image.setCellValueFactory(new PropertyValueFactory<>("imageView"));

        }
        catch (NullPointerException | SQLException e){
            e.printStackTrace();
        }catch (IllegalArgumentException exception){
                Methods.setText(error,"Image/s not found");
                initialize(location,resources);
            }
    }
    public void addNewBook(ActionEvent event)  {
        try {
            new Methods().switchScenes(event,"addBook.fxml");
        } catch (IOException e) {
            error.setText("Error occured");
            e.printStackTrace();
        }
    }
    public void back(ActionEvent event)  {
        try {
            new Methods().switchScenes(event,"librarian.fxml");
        } catch (IOException e) {
            error.setText("Error occured");
            e.printStackTrace();
        }

    }
    public void add(){
        try {
        Books a = table.getSelectionModel().getSelectedItem();
        DBUtil.addExistingbook(a.getTitle(),true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("UPDATE");
        alert.setContentText("Book has been added");
        alert.showAndWait();
        DBUtil.reloadTableView(table);
        search();
        } catch (SQLException e) {
            error.setText("Error detected");
            e.printStackTrace();
        }catch (NullPointerException exception){
            Methods.setText(error,"Select a Book");
        }
    }
    public void remove(){
        try {
            Books a = table.getSelectionModel().getSelectedItem();
            DBUtil.addExistingbook(a.getTitle(),false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("UPDATE");
            alert.setContentText("Book has been removed");
            alert.showAndWait();
            DBUtil.reloadTableView(table);
            search();
        } catch (SQLException e) {
            error.setText("Error detected");
            throw new RuntimeException(e);
        }
        catch (NullPointerException exception){
            Methods.setText(error,"Select a Book");
        }
    }
    public void search(){
        try {
            String search = tfsearch.getText().replaceAll("","%");
                search = search.replaceAll(" ","%");
                System.out.println(search);
                DBUtil.searchBooksTable(search,table);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    public void setImage(){
//        try {
//            Books a = table.getSelectionModel().getSelectedItem();
//            System.out.println(a.getAuthor());
//            imageView.setPreserveRatio(false);
//            if (a.getImageView() != null) {
//                System.out.println("display");
//                ImageView image = a.getImageView();
//                imageView.setImage(image);
//                noImageAvailible.setVisible(false);
//            }
//            else{
//                imageView.setImage(null);
//                noImageAvailible.setVisible(true);
//            }
//        }catch (RuntimeException runtimeException){
//            Methods.setText(error,"Error Occured");
//            runtimeException.printStackTrace();
//        }
//    }
    public Books getBook(){
        return bookInEditBook;
    }
    public void editBook(ActionEvent event){
        try {
            if(table.getSelectionModel().getSelectedItem() != null){
                new Methods().switchScenes(event,"editBookAdvanced.fxml");
            }
            else {
                Methods.setText(error,"Select a Book");;
            }
        } catch (NullPointerException e) {
            error.setText("Select a book");
            e.printStackTrace();
        } catch (Exception e){
            error.setText("Error occured");
            e.printStackTrace();
        }
    }
}


