package com.example.librarymanagementsystem;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;

public class LoginController {
//public static String[] issuedBy;
public static Users issueCredentials;
    public static ActionEvent event1;
    @FXML
    private TextField tfcontact;
    @FXML
    private Button btncreateaccount;
    @FXML
    private Button btnlogin;
    @FXML
    private Label sign_in_error;
    @FXML
    private Label sign_up_error;
    @FXML
    private Label sign_up_warning;
    @FXML
    private PasswordField signinpassword;
    @FXML
    private TextField signinusername;
    @FXML
    private PasswordField signuppassword1;
    @FXML
    private PasswordField signuppassword2;
    @FXML
    private TextField signupusername;

    @FXML
    private void usernameWarning(){
        sign_up_warning.setText("Username must be atleast 5 digits");
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException, SQLException {

        String username = signupusername.getText().trim();
        String password_1 = signuppassword1.getText();
        String password_2 = signuppassword2.getText();
        String contact = tfcontact.getText();


        if (checkUsername(username) && checkPassword(password_1, password_2) && checkContact(contact)) {
            try{
            if(DBUtil.requestUser(username,password_1,contact)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("MESSAGE");
                alert.setHeaderText("Your request for creating an account has been submitted");
                alert.showAndWait();
            }
            else{
                Methods.setText(sign_up_error,"USERNAME ALREADY EXISTS");
            }
            }   catch (Exception e){ Methods.setText(sign_up_error,"Error occured"); }
        }

    }

    @FXML
    void loginToAccount(ActionEvent event)  {

        String username = signinusername.getText().trim();
        String password = signinpassword.getText();
        try {
            if (DBUtil.logInAdmin(username, password)) {
                issueCredentials = Users.UsersBuilder.anUsers().buildName(username).buildPassword(password).build();
                new Methods().switchScenes(event, "librarian.fxml");
            } else if (DBUtil.logInUser(username, password)) {
                issueCredentials = Users.UsersBuilder.anUsers().buildName(username).buildPassword(password).build();
                event1 = event;
                new Methods().switchScenes(event, "users.fxml");
            } else
                Methods.setText(sign_in_error, "INCORRECT USERNAME OR PASSWORD!!!");
        } catch (Exception e) {
            e.printStackTrace();
            Methods.setText(sign_in_error,"Error occured");
        }
    }

    private boolean checkUsername(String username) throws IOException {
        if (username.length() < 5) {
            Methods.setText(sign_up_error,"USERNAME IS TOO SHORT!!");
            return false;
        }
        return true;
    }

     boolean checkPassword(String password_1,String password_2){

        if (password_1.length() < 5) {
            Methods.setText(sign_up_error,"PASSWORD IS TOO SHORT!!");
            return false;
        }
        if (!(password_1.equals(password_2))){
            Methods.setText(sign_up_error,"PASSWORDS DO NOT MATCH!!");
            return false;
        }
        return true;
    }
    boolean checkContact(String contact){
        if(contact.length() == 11){
            char[] chars = contact.toCharArray();
            StringBuilder sb = new StringBuilder();
            for(char c : chars){
                if(Character.isDigit(c)){
                    sb.append(c);
                }
            }
            if(sb.toString().equals(contact)){
                return true;
            }
        }
        Methods.setText(sign_up_error,"Wrong format of Contact");
        return false;
    }

    public void setLibrarianFXML(){

    }



}

