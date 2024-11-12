package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Ddashboardcontroller {

    @FXML
    private Button prcreate;

    @FXML
    private Button predit;

    @FXML
    private Button logout;

    @FXML
    private Button cancel;

    public void loadcreate() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dcreate.fxml"));
            Parent root = loader.load();

            // Create a new scene and set it to a new stage
            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Create Prescription");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) prcreate.getScene().getWindow();

            // Close the current stage
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadedit() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("dview.fxml"));
            Parent root = loader.load();


            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Manage Prescriptions");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) predit.getScene().getWindow();


            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cancelaction(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void logoutfunction() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
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
