package com.example.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Dcreatecontroller {
    @FXML
    private Button pfind;

    @FXML
    private Button canceltodash;

    @FXML
    private Button createsubmit;

    @FXML
    private Label loggeddoctor;

    @FXML
    private Label patientnamelabel;

    @FXML
    private Label patientagelabel;

    @FXML
    private Label errormessagelabel;

    @FXML
    private Label patientfinderror;

    @FXML
    private TextField pidfind;

    @FXML
    private TextArea pmedicines;

    @FXML
    private TextArea pnotes;

    @FXML
    private DatePicker pdate;

    public String patientID;

    int docID = LoginController.userID;

    public void initialize() {
        loggeddoctor.setText(LoginController.userName);
    }
    private static final int ERROR_MESSAGE_DURATION = 5;

    public void findPatient(ActionEvent event) {

        try {
            if (pidfind.getText().isEmpty()) {
                setErrorWithTimer(patientfinderror, "Enter Patient ID");
            } else {
                patientID = pidfind.getText();
                if (!patientID.matches("[0-9]+")) {
                    setErrorWithTimer(patientfinderror, "Invalid Patient ID");
                } else {
                    Connection connection = null;
                    DBconnection dbUtils = new DBconnection();
                    connection = dbUtils.getConnection();

                    String sql = "SELECT * FROM patient WHERE pid=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, patientID);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        patientnamelabel.setText(resultSet.getString("name"));
                        patientagelabel.setText(resultSet.getString("age"));
                    } else {
                        setErrorWithTimer(patientfinderror, "Patient not found");
                }
            }
            }

    } catch (Exception e) {
        setErrorWithTimer(patientfinderror, "Error finding patient");
    }
    }

    public void submitCreate(ActionEvent event) {
        if (pidfind.getText().isEmpty() || pmedicines.getText().isEmpty() || pnotes.getText().isEmpty() || pdate.getValue() == null) {
            setErrorWithTimer(errormessagelabel, "Fill all fields");
            return;
        }

        try (Connection connection = new DBconnection().getConnection()) {
            String sql = "INSERT INTO prescription (pid, sid, medicine, note, date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Get date from DatePicker and convert it to a string or SQL Date
            LocalDate selectedDate = pdate.getValue();
            preparedStatement.setString(5, selectedDate.toString()); // Optionally, you can convert this to java.sql.Date if needed

            preparedStatement.setString(1, pidfind.getText());
            preparedStatement.setInt(2, docID);
            preparedStatement.setString(3, pmedicines.getText());
            preparedStatement.setString(4, pnotes.getText());

            preparedStatement.executeUpdate();
            setErrorWithTimer(errormessagelabel, "Prescription created");
            clearFields();

        } catch (Exception e) {
            setErrorWithTimer(errormessagelabel, "Error creating prescription");
            clearFields();
            e.printStackTrace();
        }
    }

    public void clearFields() {
        pidfind.clear();
        pmedicines.clear();
        pnotes.clear();
        pdate.setValue(null);
        patientnamelabel.setText("");
        patientagelabel.setText("");
        patientID = null;

    }
    private void setErrorWithTimer(Label label, String message) {
        label.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MESSAGE_DURATION));
        pause.setOnFinished(e -> label.setText(""));
        pause.play();
    }

    public void cancelaction() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ddashboard.fxml"));
            Parent root = loader.load();


            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Doctor Dashboard");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) canceltodash.getScene().getWindow();


            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
