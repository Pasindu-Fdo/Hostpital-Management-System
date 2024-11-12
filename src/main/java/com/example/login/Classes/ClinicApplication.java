package com.example.login.Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(ClinicApplication.class.getResource("Clinic-view.fxml"));

            // Set the scene
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Patient Management System");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}