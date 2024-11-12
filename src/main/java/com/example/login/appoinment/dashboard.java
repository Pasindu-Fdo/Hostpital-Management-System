package com.example.login.appoinment;

import com.example.login.Main;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class dashboard {
    @FXML
    private Label Derrormsgid;
    @FXML
    private Button logout;
    @FXML
    private Button cancel;


    private static final int ERROR_MESSAGE_DURATION = 5;

    @FXML
    private void view(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/login/appoinment/viewAppointment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e) {
            setErrorWithTimer(Derrormsgid, e.getMessage());
            e.printStackTrace(); // This will print the stackNon-static method 'setAppointmentData(int, int, java. lang. String, java. lang. String)' cannot be referenced from a static context trace to the console for debugging
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/login/appoinment/addAppoinment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e) {
            setErrorWithTimer(Derrormsgid, e.getMessage());
            e.printStackTrace(); // This will print the stackNon-static method 'setAppointmentData(int, int, java. lang. String, java. lang. String)' cannot be referenced from a static context trace to the console for debugging
        }
    }


    public void cancleButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/mainrdash.fxml"));
            Parent root = loader.load();

            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Receptionist Dashboard");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) cancel.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setErrorWithTimer(Label label, String message) {
        label.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MESSAGE_DURATION));
        pause.setOnFinished(e -> label.setText("")); // Clear the message after the timer
        pause.play();
    }
    public void logoutfunction() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/login.fxml"));
            Parent root = loader.load();


            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Login");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) logout.getScene().getWindow();


            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}