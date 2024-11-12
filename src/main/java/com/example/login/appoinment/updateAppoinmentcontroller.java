package com.example.login.appoinment;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class updateAppoinmentcontroller {
    @FXML
    private Button UCadd;
    @FXML
    private Button UBPsearch;
    @FXML
    private Label UPerrormsgid;
    @FXML
    private Label Ppname;
    @FXML
    private Label Date;
    @FXML
    private Label Time;
    @FXML
    private Label Docname;
    @FXML
    private Label Uerrormsgid;
    @FXML
    private DatePicker AUdate;
    @FXML
    private TextField AUtime;
    //@FXML
    //private static Label doctorNameField;
    //@FXML
    //private static Label dateField;
    //@FXML
    //private static Label timeField;


    private static final int ERROR_MESSAGE_DURATION = 5;

    private int patientId;
    private int doctorId;
    private String date;
    private String time;

    // Method to set appointment data
    public void setAppointmentData(int patientId, int doctorId, String date, String time) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        showdata();
    }

    public void showdata(){
        try {
            Connection connection = null;
            DButils dbUtils = new DButils();
            connection = dbUtils.getConnection();

            // Query for patient name
            String patientName = "SELECT name FROM patient WHERE pid=?";
            PreparedStatement preparedStatement = connection.prepareStatement(patientName);
            preparedStatement.setInt(1, patientId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Ppname.setText(resultSet.getString("name"));
            }
            resultSet.close(); // Close ResultSet and PreparedStatement after each use
            preparedStatement.close();

            // Query for doctor name
            String doctorName = "SELECT name FROM staff WHERE sid=?";
            PreparedStatement docpreparedStatement = connection.prepareStatement(doctorName);
            docpreparedStatement.setInt(1, doctorId);
            ResultSet docresultSet = docpreparedStatement.executeQuery(); // Corrected here

            if (docresultSet.next()) {
                Docname.setText("Dr. "+docresultSet.getString("name")); // Corrected here
            }
            docresultSet.close();
            docpreparedStatement.close();

            // Set date and time fields
            Date.setText(date);
            Time.setText(time);
        } catch (Exception e) {
            setErrorWithTimer(Uerrormsgid, e.getMessage());
        }
    }

    public void updateAction(ActionEvent event) {
        try{
            Connection connection = null;
            DButils dbUtils = new DButils();
            connection = dbUtils.getConnection();

            if (AUdate.getValue() == null && AUtime.getText().isEmpty()){
                setErrorWithTimer(Uerrormsgid,"Enter New Date and Time");
            } else if (AUdate.getValue() == null) {
                setErrorWithTimer(Uerrormsgid,"Select New Date");
            } else if (AUtime.getText().isEmpty()) {
                setErrorWithTimer(Uerrormsgid,"Enter New Time");
            }else{
                String newDate = AUdate.getValue().toString();
                String newTime = AUtime.getText().toString();

                String sql = "UPDATE appointment SET date = ?, time = ? WHERE Sid = ? AND pid = ? AND date=? AND time=?";

                PreparedStatement pst = connection.prepareStatement(sql);
                pst.setString(1, newDate);        // Set new date
                pst.setString(2, newTime);        // Set new time
                pst.setInt(3, doctorId);               // Assuming Did is the doctor ID
                pst.setInt(4, patientId);
                pst.setString(5,date);
                pst.setString(6,time);// Assuming Pid is the patient ID

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    Uerrormsgid.setStyle("-fx-text-fill: " + "Blue" + ";");
                    setErrorWithTimer(Uerrormsgid, "Appointment updated successfully");
                    cancleButtonAction(null);
                } else {
                    Uerrormsgid.setStyle("-fx-text-fill: " + "Red" + ";");
                    setErrorWithTimer(Uerrormsgid, "No appointment found to update");
                }

                pst.close(); // Close the PreparedStatement

            }
        } catch (Exception e) {
            setErrorWithTimer(Uerrormsgid, e.getMessage());
        }
    }




    public void cancleButtonAction(ActionEvent event) {
        try {
            Stage stage = (Stage) UCadd.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.err.println("Error closing the window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearInputFields() {
        AUdate.setValue(null);
        AUtime.clear();
    }


    private void setErrorWithTimer(Label label, String message) {
        label.setStyle("-fx-text-fill: " + "Red" + ";");
        label.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MESSAGE_DURATION));
        pause.setOnFinished(e -> label.setText("")); // Clear the message after the timer
        pause.play();
    }
}