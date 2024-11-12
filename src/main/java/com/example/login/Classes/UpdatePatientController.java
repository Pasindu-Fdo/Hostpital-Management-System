package com.example.login.Classes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class UpdatePatientController {
    @FXML
    private TextField patientInfoName;
    @FXML
    private TextField patientId;
    @FXML
    private TextField patientInfoAge;
    @FXML
    private TextField hometown;
    @FXML
    private TextField patientInfoPhone;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label infoBox;
    @FXML
    private Button cancel;

    public Integer PID;

    private final PatientDAO patientDAO = new PatientDAO();

    @FXML
    public void initialize() {
        updateButton.setOnAction(event -> handleUpdatePatient());
        searchButton.setOnAction(event -> handleSearchPatient());
    }

    @FXML
    private void handleSearchPatient() {
        String searchName = searchField.getText();

        // Search for a patient by name using PatientDAO
        Patient1 patient = patientDAO.findPatient1(searchName);

        // Display patient information or not found message
        if (patient != null) {
            PID = patient.getPid();
            patientId.setText(patient.getPid().toString());
            patientInfoName.setText(patient.getName());
            patientInfoAge.setText(patient.getAge());
            hometown.setText(patient.gethometown());
            patientInfoPhone.setText(patient.getTelephone());
        } else {
            infoBox.setVisible(true);
            infoBox.setText("Patient not found.");
        }
    }


    @FXML
    private void handleUpdatePatient() {
        String oldName = searchField.getText();

        // Create a Patient object with updated data
        Patient1 patient = new Patient1(PID,patientInfoName.getText(), patientInfoAge.getText(), patientId.getText(), patientInfoPhone.getText());
        patientDAO.updatePatient(patient, oldName);

        // Display confirmation message and clear fields
        infoBox.setVisible(true);
        infoBox.setText("Patient updated: " + patient.getName());
        clearInputFields();
    }

    private void clearInputFields() {
        patientInfoName.clear();
        patientInfoAge.clear();
        patientId.clear();
        patientInfoPhone.clear();
        searchField.clear();
    }
    public void cancel(){
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxmls/ReDashboard.fxml"));
            Parent root = loader.load();

            // Create a new scene and set it to a new stage
            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Dashboard");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) cancel.getScene().getWindow();

            // Close the current stage
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
