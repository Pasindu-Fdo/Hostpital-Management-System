package com.example.login.Classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReDashboardController {
    @FXML private Button addpatient;
    @FXML private Button searchpatient;
    @FXML private Button deletepatient;
    @FXML private Button updatepatient;
    @FXML private Button cancel;
    @FXML private Button logout;


    public void addpatient() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxmls/Add-view.fxml"));
            Parent root = loader.load();

            // Create a new scene and set it to a new stage
            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Add Patient");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) addpatient.getScene().getWindow();

            // Close the current stage
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void viewpatient() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxmls/Search-view.fxml"));
            Parent root = loader.load();

            // Create a new scene and set it to a new stage
            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("View Patient");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) searchpatient.getScene().getWindow();

            // Close the current stage
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void removepatient() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxmls/Delete-view.fxml"));
            Parent root = loader.load();

            // Create a new scene and set it to a new stage
            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Remove Patient");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) deletepatient.getScene().getWindow();

            // Close the current stage
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void updatepatient(ActionEvent event) {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxmls/Update-view.fxml"));
            Parent root = loader.load();

            // Create a new scene and set it to a new stage
            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Update Patient");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) updatepatient.getScene().getWindow();

            // Close the current stage
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void cancelaction(ActionEvent event) {
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
    public void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/Login.fxml"));
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
        }}
}

