package com.example.librarymanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class AdminUsers implements Initializable {
    ObservableList<Users> obList = FXCollections.observableArrayList();
    @FXML
    Label ibError;
    @FXML
    TextField ibTextField;
@FXML
TableColumn<Users,Integer> irCopiesAvailible;
    @FXML
    private TableColumn<Users, String> AUcontact;
    @FXML
    private TableColumn<Users, String> AUdate;
    @FXML
    private TableColumn<Users, String> AUname;
    @FXML
    private TableView<Users> AUtable;
    @FXML
    private Button AUbtnremove;
    @FXML
    private Label AUerror;
    @FXML
    private TextField AUtfenterName;
    @FXML
    private TableColumn<Users, String> irBook;
    @FXML
    private TableView<Users> irTable;
    @FXML
    private TableColumn<Users, String> irUserName;
    @FXML
    private Button btnIRAccept;
    @FXML
    private Button btnIRDelete;
    @FXML
    private TableColumn<Users, String> ibBooks;
    @FXML
    private TableColumn<Users, String> ibDateIssued;
    @FXML
    private TableColumn<Users, String> ibDaysToReturn;
    @FXML
    private TableColumn<Users, String> ibName;
    @FXML
    private TableView<Users> ibTable;
    @FXML
    private Label irError;
    @FXML
    private Button ibReturnBook;
    @FXML
    private TableColumn<Users, String> TcfineAmount;
    @FXML
    private TableColumn<Users, String> TcfineReason;
    @FXML
    private TableColumn<Users,Integer> TcfineUserName;
    @FXML
    private TableView<Users> tableFine;
    @FXML
    private CheckBox CbBrokenBinding;
    @FXML
    private CheckBox CbExcessiveWriting;
    @FXML
    private CheckBox CbMissingBarcode;
    @FXML
    private CheckBox CbTornPages;
    @FXML
    private CheckBox CbWaterDamage;
    @FXML
    private CheckBox CbBookLost;
    @FXML
    private TextField TfTotalPercentage;
    @FXML
    private TextField TfBookPrice;
    @FXML
    private TextField TfCalculateFine;
    @FXML
    private TextField TfAssignFine;
    @FXML
    private Button BtnCalculateFine ;
    @FXML
    private Button BtnAssignFine ;
    @FXML
    private Button BtnSubmit ;
    @FXML
    private TextField TfFineReason;
    @FXML
    private TextArea TaFineReason;
@FXML
private VBox VBoxCheckBox;
@FXML
private Label labelFineError;
@FXML
private ListView<String> LvFineSearch;
@FXML
private TextField TfFineAmount;
@FXML
private TextField TfSearchFine;
@FXML
private Button BtnRemoveFine;
@FXML
private Label labelFineSearch;
    private String[][] issueRequests;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            DBUtil.searchListViewUsers(TfAssignFine.getText(),LvFineSearch);
            setDatainAUTable();
            setDataInIRTable();
            setDataInIBTable();
            setDataInFineTable();

        } catch (SQLException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public void back(ActionEvent event){
        try {
            new Methods().switchScenes(event,"librarian.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDataInFineTable(){
        try {
        tableFine.setItems(DBUtil.fineTableView(TfSearchFine.getText()));
        TcfineUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TcfineAmount.setCellValueFactory(new PropertyValueFactory<>("fineAmount"));
        TcfineReason.setCellValueFactory(new PropertyValueFactory<>("fineReason"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setDatainAUTable() throws SQLException {
        ObservableList<Users> observableListAUTable = DBUtil.tableAllUsers(AUtfenterName.getText());
        AUtable.setItems(observableListAUTable);
        AUname.setCellValueFactory(new PropertyValueFactory<>("name"));
        AUcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        AUdate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }
    public void setDataInIRTable() throws SQLException {
        ObservableList<Users> observableListIRTable = DBUtil.issuedRequestsTable();
        irTable.setItems(observableListIRTable);
        irUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        irBook.setCellValueFactory(new PropertyValueFactory<>("issueRequest"));
        irCopiesAvailible.setCellValueFactory(new PropertyValueFactory<>("copiesAvailible"));

    }
    public void setDataInIBTable(){
        try {
            ObservableList<Users> observableListIBTable = DBUtil.ibTable(ibTextField.getText());
            ibName.setCellValueFactory(new PropertyValueFactory<>("name"));
            ibBooks.setCellValueFactory(new PropertyValueFactory<>("bookIssued"));
            ibDateIssued.setCellValueFactory(new PropertyValueFactory<>("dateOfIssue"));
            ibDaysToReturn.setCellValueFactory(new PropertyValueFactory<>("daysToReturn"));
            ibTable.setItems(observableListIBTable);

        } catch (SQLException | ParseException e) {
            Methods.setText(ibError,"Error occured");
            e.printStackTrace();
        }
    }

    public void removeFromAllUsers(){
        try {
            if(DBUtil.deleteUser(AUtfenterName.getText())){
            Methods.setText(AUerror,"Deleted user");
            AUtfenterName.setText("");
            setDatainAUTable();
            setDataInFineTable();
            setDataInIRTable();
            setDataInIBTable();
            }
            else{
                AUerror.setText("Select a user");
            }
        }
        catch (SQLException e) {
            Methods.setText(AUerror,"Error Occured");
            e.printStackTrace();
        }

    }
    public void selectUserFromAllUsers(){

       Users a = AUtable.getSelectionModel().getSelectedItem();
       if(a!=null){
           AUtfenterName.setText(a.getName());
       }
       else{
           Methods.setText(AUerror,"Select a row");
       }
    }
    public void searchfromAllUsers(){
        try {
            DBUtil.searchUsers(AUtfenterName.getText(),AUtable);
        } catch (SQLException e) {
            Methods.setText(AUerror,"Error Occured");
            e.printStackTrace();
        }
    }
    public void setBtnIRAccept() {
        try {
            Users a;
            if ((a = irTable.getSelectionModel().getSelectedItem()) != null) {
                if(DBUtil.issueBook(a.getName(),a.getIssueRequest())){
                    //copiesborrowed+1
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("All copies issued");
                    alert.showAndWait();
                }
                setDataInIRTable();
                setDataInIBTable();
            } else {
                irError.setText("Select a user");
            }
        } catch (SQLException e) {
            Methods.setText(irError,"Error Occured");
            e.printStackTrace();
        }
    }
    public void setBtnIRDelete() {
        try {

        Users a;
        if((a = irTable.getSelectionModel().getSelectedItem()) != null){
            DBUtil.denyIssue(a.getName());
            setDataInIRTable();
        }
        else{
            Methods.setText(irError,"Select a user");
        }
        }catch (SQLException e) {
            Methods.setText(irError,"Error Occured");
            e.printStackTrace();
        }

    }
    public void searchFromIssuedBooks(){
        try {
           ObservableList<Users> observableList =  DBUtil.ibTable(ibTextField.getText());
           ibTable.setItems(observableList);
        } catch (SQLException | ParseException e) {
            Methods.setText(ibError,"Error occured");
            e.printStackTrace();
        }

    }
    public void setOnCheckBoxClicked(){
        int finePercentage = 0;
        if(CbBookLost.isSelected()){
            finePercentage += 120;
        }
        if(CbBrokenBinding.isSelected()){
            finePercentage += 30;
        }
        if(CbExcessiveWriting.isSelected()){
            finePercentage += 30;
        }
        if(CbMissingBarcode.isSelected()){
            finePercentage += 15;
        }if(CbTornPages.isSelected()){
            finePercentage += 70;
        }
        if(CbWaterDamage.isSelected()){
            finePercentage += 70;
        }
        TfTotalPercentage.setText(String.valueOf(finePercentage));
    }
    public void setBtnCalculateFine(){
        int finePercentage;
        int bookPrice;
        double fineAmount;
        if(!TfTotalPercentage.getText().matches("[0-9]+")){
            Methods.setText(labelFineError,"Enter digits only in total percentage");
            return;
        }
        finePercentage = Integer.parseInt(TfTotalPercentage.getText());
        if(TfBookPrice.getText().equals("")){
            Methods.setText(labelFineError,"Enter book price");
        }
        if(!TfBookPrice.getText().matches("[0-9]+")){
            Methods.setText(labelFineError,"Enter digits only in book price");
            return;
        }
        bookPrice = Integer.parseInt(TfBookPrice.getText());
        fineAmount = (finePercentage/100.0)*bookPrice;
        TfCalculateFine.setText(String.valueOf(fineAmount));
    }
    public void setTextFieldSearchUser(){
        try {
            DBUtil.searchListViewUsers(TfAssignFine.getText(),LvFineSearch);
        } catch (SQLException | ParseException e) {
            Methods.setText(labelFineError,"Error Occured");
            e.printStackTrace();
        }
    }
    public void setBtnAssignFine(){
        try {
            if(!DBUtil.checkUserExists(TfAssignFine.getText())){
            Methods.setText(labelFineError,"Enter correct User Name");
            return;
        }
        if(TfFineAmount.getText().equals("")){
            Methods.setText(labelFineError,"Enter fine amount");
            return;
        }
        if(!TfFineAmount.getText().matches("[0-9]+")){
            Methods.setText(labelFineError,"Enter only digits");
            return;
        }
        if(TaFineReason.getText().equals("")){
            Methods.setText(labelFineError,"Enter Fine reason");
            return;
        }
        DBUtil.assignFine(TfAssignFine.getText(),TaFineReason.getText(),Integer.parseInt(TfFineAmount.getText()));
        setDataInFineTable();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("A Fine of Rs"+TfFineAmount.getText()+" has been\n" +
                            " assigned to "+TfAssignFine.getText());
        alert.showAndWait();
        setDataInFineTable();

        }catch (SQLException e) {
            Methods.setText(labelFineError,"Error Occured");
            e.printStackTrace();
        }
    }
    public void listViewGetSelection(){
        String selected = "";
        if(!(selected = LvFineSearch.getSelectionModel().getSelectedItem()).equals("")
            && (LvFineSearch.getSelectionModel().getSelectedItem() != null)){
            TfAssignFine.setText(selected);
        }

    }
    public void setTextInSearchFine(){
        Users a;
        if((a = tableFine.getSelectionModel().getSelectedItem())!= null ){
            TfSearchFine.setText(a.getName());
        }
    }
    public void setBtnRemoveFine(){
        try {
            if(DBUtil.removeFine(TfSearchFine.getText())){
                TfSearchFine.setText("");
                setDataInFineTable();
            }
            else{
                Methods.setText(labelFineSearch,"Select a user");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
