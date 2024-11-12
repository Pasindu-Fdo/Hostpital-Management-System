package com.example.login.Classes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddPatientController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField hometown;
    @FXML
    private Label infoBox;
    @FXML
    private Label pidshow;

    public static String pid;

    @FXML
    private Button addPatient;

    @FXML
    private Button cancel;

    private final PatientDAO patientDAO = new PatientDAO(); // Initialize PatientDAO

    @FXML
    public void initialize() {
        addPatient.setOnAction(event -> handleAddPatient());

    }

    @FXML
    private void handleAddPatient() {

        String name = nameField.getText();
        String age = ageField.getText();
        String telephone = telephoneField.getText();
        String home = hometown.getText();
        // Create a new Patient object and add it to the database
        Patient patient = new Patient(name, age, home, telephone);
        patientDAO.addPatient(patient);

        // Display confirmation message and clear fields
        infoBox.setVisible(true);
        infoBox.setText("Patient added: " + name);
        pidshow.setText("Patient ID: " +pid);
        clearInputFields();
    }

    private void clearInputFields() {
        nameField.clear();
        ageField.clear();
        hometown.clear();
        telephoneField.clear();
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
