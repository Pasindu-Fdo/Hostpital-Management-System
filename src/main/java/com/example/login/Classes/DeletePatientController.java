package com.example.login.Classes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeletePatientController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextArea resultField;

    @FXML
    private Button cancel;

    private final PatientDAO patientDAO = new PatientDAO();

    @FXML
    public void initialize() {
        searchButton.setOnAction(event -> handleSearchPatient());
        deleteButton.setOnAction(event -> handleDeletePatient());
    }

    @FXML
    private void handleSearchPatient() {
        String searchName = searchField.getText();

        // Search for a patient by name using PatientDAO
        Patient patient = patientDAO.findPatient(searchName);

        // Display patient information or not found message
        if (patient != null) {
            String patientInfo = "Name: " + patient.getName() + "\n" +
                    "Age: " + patient.getAge() + "\n" +
                    "Gender: " + patient.gethometown() + "\n" +
                    "Phone: " + patient.getTelephone();

            resultField.setText(patientInfo);  // Assuming `patientInfoTextArea` is your TextArea's ID
        } else {
            resultField.setText("Patient not found.");
        }
    }

    @FXML
    private void handleDeletePatient() {
        String name = searchField.getText();

        // Delete patient using PatientDAO
        patientDAO.deletePatient(name);

        resultField.setText("Patient deleted: " + name);
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
