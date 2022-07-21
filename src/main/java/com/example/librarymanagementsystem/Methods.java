package com.example.librarymanagementsystem;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Methods {
    public static void setText(Label label, String text){
        label.setText(text);
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> label.setText(null));
        pause.play();
    }

    public static String currentTime(){
        java.util.Date dt = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentTime = sdf.format(dt);
        return currentTime;
    }

    public static long differenceBetweenDatesinMilli(String startDate,String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        // Try Block
        // parse method is used to parse
        // the text from a string to
        // produce the date
        Date d1 = sdf.parse(startDate);
        Date d2 = sdf.parse(endDate);

        // Calucalte time difference
        // in milliseconds
        long difference_In_Time = d2.getTime() - d1.getTime();
        return difference_In_Time;
    }

    public static int bookIssueReturnTime(String issueDateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        // Try Block
        // parse method is used to parse
        // the text from a string to
        // produce the dateToReturn
        Date dateIssued = sdf.parse(issueDateString);
        long dateToReturnInMilli = dateIssued.getTime()+1209600000L;//adding 14 days in milli
        Date dateToReturn = new Date(dateToReturnInMilli);
        String dateToReturnString = sdf.format(dateToReturn);
        Date currentDate = new Date();
        String currentDateString = sdf.format(currentDate);
        int daysToReturn = (int) ((differenceBetweenDatesinMilli(currentDateString,dateToReturnString) / (1000 * 60 * 60 * 24)));
        return daysToReturn;
    }

    public void switchScenes(ActionEvent event, String fxml) throws IOException{
        System.out.println("point 1");
        //Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        FXMLLoader loader = new FXMLLoader(Methods.class.getResource(fxml));
        System.out.println("point 2");
        Parent root = loader.load();
        System.out.println("point 3");
        Scene scene = new Scene(root);
        System.out.println("point 4");
        Stage primaryStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        System.out.println("point 5");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

    }
}
