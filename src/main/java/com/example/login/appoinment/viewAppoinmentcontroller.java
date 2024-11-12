package com.example.login.appoinment;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.geometry.Pos;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.example.login.appoinment.updateAppoinmentcontroller;

public class viewAppoinmentcontroller {
    @FXML
    private Button VCadd;
    @FXML
    private Button VBPsearch;
    @FXML
    private TextField Pname;
    @FXML
    private Label VPerrormsgid;
    @FXML
    private Label Verrormsgid;
    @FXML
    private VBox Vbox;


    private static final int ERROR_MESSAGE_DURATION = 5;


    public void PsearchButtonAction(ActionEvent event) {
        try {
            if (Pname.getText().isEmpty()) {
                setErrorWithTimer(VPerrormsgid, "Enter Patient name");
            } else {
                String patientName = Pname.getText();
                if (!patientName.matches("[a-zA-Z\\s]+")) {
                    setErrorWithTimer(VPerrormsgid, "Enter valid name");
                }else{
                    Connection connection = null;
                    DButils dbUtils = new DButils();
                    connection = dbUtils.getConnection();

                    String sql="SELECT * FROM patient WHERE name=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, patientName);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {

                        int patientId = resultSet.getInt("pid");

                        String appointmentsQuery = "SELECT * FROM appointment WHERE pid=? AND arrive='No'";
                        PreparedStatement appointmentsStmt = connection.prepareStatement(appointmentsQuery);
                        appointmentsStmt.setInt(1, patientId);
                        ResultSet appointmentsResult = appointmentsStmt.executeQuery();

                        Vbox.getChildren().clear(); // Clear previous results

                        while (appointmentsResult.next()) {
                            int doctorId = appointmentsResult.getInt("sid");
                            String date = appointmentsResult.getString("date");
                            String time = appointmentsResult.getString("time");

                            // Fetch doctor's name
                            String doctorQuery = "SELECT name FROM staff WHERE sid=? AND jobtitle='Doctor'";
                            PreparedStatement doctorStmt = connection.prepareStatement(doctorQuery);
                            doctorStmt.setInt(1, doctorId);
                            ResultSet doctorResult = doctorStmt.executeQuery();

                            String doctorName = "";
                            if (doctorResult.next()) {
                                doctorName = doctorResult.getString("name");
                            }

                            // Display appointment info in VBox
                            Label rowLabel = new Label("    Dr. "+doctorName + "                          " + date + "                                " + time);
                            Label emptyLable=new Label(" ");

                            Button updateButton = new Button("Update");
                            Button deleteButton = new Button("Delete");

                            updateButton.setOnAction(e -> handleUpdateAction(patientId,doctorId, date, time));
                            deleteButton.setOnAction(e -> handleDeleteAction(patientId,doctorId, date, time));

                            HBox rowContainer = new HBox(10);  // 10 is the spacing between elements
                            rowContainer.setAlignment(Pos.CENTER_LEFT);
                            rowContainer.getChildren().addAll(rowLabel, updateButton, deleteButton);

                            Vbox.getChildren().add(rowContainer);
                            Vbox.getChildren().add(emptyLable);

                            doctorResult.close();
                            doctorStmt.close();
                        }

                        appointmentsResult.close();
                        appointmentsStmt.close();
                    }else{
                        VPerrormsgid.setStyle("-fx-text-fill: " + "Red" + ";");
                        setErrorWithTimer(VPerrormsgid, "Patient Record Not Available");
                        clearPname();
                    }

                }
            }
        } catch (Exception e) {
            setErrorWithTimer(VPerrormsgid, "Unexpected error occurred.");
            e.printStackTrace();
        }
    }

    // In viewAppoinmentcontroller.java
    private void handleUpdateAction(int patientId, int doctorId, String date, String time) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("updateAppoinment.fxml"));
            Parent root = loader.load();
            updateAppoinmentcontroller controller = loader.getController();
            controller.setAppointmentData(patientId,doctorId, date, time);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
            clearFields();
        }catch (Exception e) {
            setErrorWithTimer(Verrormsgid, e.getMessage());
            e.printStackTrace(); // This will print the stackNon-static method 'setAppointmentData(int, int, java. lang. String, java. lang. String)' cannot be referenced from a static context trace to the console for debugging
        }
    }

    public void handleDeleteAction(int patientId,int doctorId,String date,String time){
        try {
            Connection connection = null;
            DButils dbUtils = new DButils();
            connection = dbUtils.getConnection();

            String sql = "DELETE FROM appointment WHERE Sid = ? AND pid = ? AND date=? AND time=?";

            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, doctorId);
            pst.setInt(2, patientId);
            pst.setString(3, date);
            pst.setString(4, time);// Assuming Pid is the patient ID

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                Verrormsgid.setStyle("-fx-text-fill: " + "Blue" + ";");
                setErrorWithTimer(Verrormsgid, "Appointment Deleted");
                PsearchButtonAction(null);
            } else {
                Verrormsgid.setStyle("-fx-text-fill: " + "Red" + ";");
                setErrorWithTimer(Verrormsgid, "No appointment found to update");
            }
        } catch (Exception e) {
            setErrorWithTimer(Verrormsgid, e.getMessage());
        }
    }


    public void cancleButtonAction(ActionEvent event) {
        try {
            Stage stage = (Stage) VCadd.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.err.println("Error closing the window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearFields() {
        Vbox.getChildren().clear();
        Pname.clear();
    }

    private void clearPname() {
        Pname.clear();
    }

    private void setErrorWithTimer(Label label, String message) {
        label.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MESSAGE_DURATION));
        pause.setOnFinished(e -> label.setText("")); // Clear the message after the timer
        pause.play();
    }
}