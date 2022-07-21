package com.example.librarymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    TextField tfsearch;
    @FXML
    Label labelReturnBook;
    @FXML
    AnchorPane apReturnBook;
    @FXML
    Button BtnReturnBook;
    URL url;
    ResourceBundle resourceBundle;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label error;
    @FXML
    private Button issue;
    @FXML
    private Button returnBook;
    @FXML
    private TextField searchBy;
    @FXML
    private TableView<Books> table;
    @FXML
    private TableColumn<Books, String> tauthor;
    @FXML
    private TableColumn<Books, String> tavailibility;
    @FXML
    private TableColumn<Books, String> tbook;
    @FXML
    private TableColumn<Books, String> tcategory;
    @FXML
    private TableColumn<Books,String> tlanguage;
    @FXML
    private TableColumn<Books, ImageView> timage;
    @FXML
    private Button btnissue;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        url = location;
        resourceBundle = resources;
        try {
            DBUtil.setAvailibilityForFreshArrival();
             int fine = DBUtil.checkFine(LoginController.issueCredentials.getName());
             if(fine != 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("You have a fine to pay!!");
              alert.setContentText("Fine Amount: "+fine);
            alert.showAndWait();
             }

            if(DBUtil.issuedBook(LoginController.issueCredentials.getName()) == null ||
                Objects.equals(DBUtil.issuedBook(LoginController.issueCredentials.getName()), "")){
                apReturnBook.setVisible(false);
                btnissue.setVisible(true);
                if(fine!=0){
                    btnissue.setVisible(false);
                }
            }
            else{
                btnissue.setVisible(false);
                apReturnBook.setVisible(true);
                labelReturnBook.setText(DBUtil.issuedBook(LoginController.issueCredentials.getName()));

            }

            DBUtil.setAvailibilityForFreshArrival();
            DBUtil.reloadTableView(table);
            tbook.setCellValueFactory(new PropertyValueFactory<>("title"));
            tauthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            tcategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            tlanguage.setCellValueFactory(new PropertyValueFactory<>("languagee"));
            tavailibility.setCellValueFactory(new PropertyValueFactory<>("bookStatus"));
            timage.setCellValueFactory(new PropertyValueFactory<>("imageView"));
            timage.setPrefWidth(50);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
    public void search(){
        String search = tfsearch.getText().replaceAll("","%");
        search = search.replaceAll(" ","");
        try {
            DBUtil.searchBooksTable(search,table);
        } catch (SQLException e) {
            error.setText("Error Occured");
            e.printStackTrace();
        }

    }
    public void selectFromTableOnMouseClicked(){
        Books a;
        if((a = table.getSelectionModel().getSelectedItem()) != null) {
            tfsearch.setText(a.getTitle());
        }
        else{
            Methods.setText(error,"Select a book");
        }

    }
    public void requestIssueBtnAction(){
        try {
            Books checkBook;
            for(int i=0; i<table.getItems().size(); i++){
                checkBook = table.getItems().get(i);
//                System.out.print(checkBook.getTitle());
//                System.out.println("   "+tfsearch.getText());
                if(checkBook.getTitle().trim().equals(tfsearch.getText().trim())){
                    if( checkBook.getBookStatus().equalsIgnoreCase("Availible")){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Request Issue: "+ tfsearch.getText());
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.isPresent() && result.get() == ButtonType.OK) {
                                DBUtil.issueRequest(tfsearch.getText());
                                alert.setAlertType(Alert.AlertType.INFORMATION);
                                alert.setHeaderText("Request Submitted");
                                alert.showAndWait();
                                break;
                        }
                    }
                    else{
                        Methods.setText(error,"Book not currently Availible");
                        break;
                    }
                }
                if(i == table.getItems().size()-1){
                    Methods.setText(error,"Enter correct name or select from table");
                }
            }
        }catch (SQLException e) {
            Methods.setText(error,"Error Occurred");
            throw new RuntimeException(e);
        }

    }

    public void setBtnReturnBook() {
        try {
            DBUtil.returnBook(LoginController.issueCredentials.getName(),DBUtil.issuedBook(LoginController.issueCredentials.getName()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Book has been returned");
            alert.showAndWait();
            initialize(url,resourceBundle);
        } catch (SQLException | ParseException e) {
            Methods.setText(error,"Error Occurred");
            e.printStackTrace();
        }
    }
    public void back(ActionEvent event){
        try {
            new Methods().switchScenes(event,"login.fxml");
        }catch (IOException e){
            System.out.println("error");
            Methods.setText(error,"Error Occurred");
        }
    }
}
