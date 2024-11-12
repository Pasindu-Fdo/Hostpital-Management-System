package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Mainrdash {
    @FXML
    private Button patient;
    @FXML
    private Button appointment;
    @FXML
    private Button cancel;
    @FXML
    private Button logout;

    public void selectappointment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/appoinment/dashboard.fxml"));
            Parent root = loader.load();

            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Dashboard");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) appointment.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectpatient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxmls/ReDashboard.fxml"));
            Parent root = loader.load();

            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Dashboard");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) patient.getScene().getWindow();
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
    public void cancelaction() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }}
