package com.example.login.appoinment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader addAppoinment = new FXMLLoader(HelloApplication.class.getResource("addAppoinment.fxml"));
            FXMLLoader updateAppoinment = new FXMLLoader(HelloApplication.class.getResource("updateAppoinment.fxml"));
            FXMLLoader viewAppoinment = new FXMLLoader(HelloApplication.class.getResource("viewAppointment.fxml"));
            FXMLLoader dashboard = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
            Scene Appointmentscene = new Scene(addAppoinment.load(), 600, 400);
            Scene dashboardload = new Scene(dashboard.load(), 600, 400);
            //Scene updateAppointmentscene = new Scene(updateAppoinment.load(), 600, 400);
            Scene viewAppointmentscene = new Scene(viewAppoinment.load(), 600, 400);
            //stage.setTitle("Hello!");
            stage.initStyle(StageStyle.UNDECORATED);
            //stage.setScene(Appointmentscene);
            //stage.setScene(updateAppointmentscene);
            stage.setScene(dashboardload);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}