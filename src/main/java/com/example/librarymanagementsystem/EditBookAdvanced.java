package com.example.librarymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditBookAdvanced extends AddBook implements Initializable  {
    @FXML
    ImageView imageView;
    @FXML
    Label searchLabel;
    @FXML
    Label error;
    Books book;
    String url;
ObservableList<String> observableList = FXCollections.observableArrayList();
    @FXML
    private Button btchangeImage;
    @FXML
    private Button btnsubmit;
    @FXML
    private ListView<String> listviewsearch;
    @FXML
    private TextField author;
    @FXML
    private TextField category;
    @FXML
    private TextField language;
    @FXML
    private TextField title;

 @Override
    public void initialize(URL location, ResourceBundle resources) {

        book = EditBook.bookInEditBook;
        author.setText(book.getAuthor());
        title.setText(book.getTitle());
        category.setText(book.getCategory());
        language.setText(book.getLanguagee());
        if(book.getImageView() != null){
            System.out.println(book.getImageView().getImage().getUrl());
            imageView.setImage(new Image(book.getImageView().getImage().getUrl()));
            imageView.setPreserveRatio(false);
        }
    }
    public void back(ActionEvent event){
        try {
            new Methods().switchScenes(event,"editBook.fxml");
        } catch (IOException e) {
            Methods.setText(error,"Error Occured");
        }
    }
    public void searchBook(KeyEvent event){
        keyTyped((TextField) event.getSource(),searchLabel,listviewsearch);
    }
     public void selectBook(){
     select(listviewsearch);
     }
     public void callAddImage(ActionEvent event){
     url = addImage(event);
     }
    public void submit(){
        try {
            if(title.getText().equals("") || author.getText().equals("") || category.getText() .equals("") || language.getText().equals("")){
            Methods.setText(error,"Fill out all fields");
            return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you want to apply these changes?");
            Optional<ButtonType> result = alert.showAndWait();
            String urlToUse = "";

            if(!result.isPresent() || result.get() != ButtonType.OK) {
                return;
            } else {
                if(url != null){
                    urlToUse = url;
                }
                else {
                    if(book.getImageView()!= null){
                        urlToUse = book.getImageView().getImage().getUrl();
                    }

                }
                if(DBUtil.updateExistingBook(title.getText(), book.getTitle(),
                    author.getText(),category.getText(),language.getText(),urlToUse)){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Book updated");
                    alert.showAndWait();
                }
                else{

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Book already exists");
                    alert.showAndWait();


                }
            }
        } catch (SQLException e) {
            Methods.setText(error,"Error Occured");
            throw new RuntimeException(e);
        }
    }
//    public static boolean updateExistingBook(     String newTitle,
                                                //    String oldTitle,
                                                //    String author,
                                                //    String category,
                                                //    String language,
                                                //    String imageURL

   //calling select on mouse clicked

}
