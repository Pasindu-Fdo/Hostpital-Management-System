package com.example.login.Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchPatientController {
    @FXML
    private TextField searchField;
    @FXML
    private Label infoBox;
    @FXML
    private TableView<Patient> tableView;  // TableView to display patient data
    @FXML
    private TableColumn<Patient, String> resultName;
    @FXML
    private TableColumn<Patient, Integer> resultAge;
    @FXML
    private TableColumn<Patient, String> resulthometown;
    @FXML
    private TableColumn<Patient, String> resultPhone;
    @FXML
    private Button searchButton;
    @FXML
    private Button cancel;

    private final PatientDAO patientDAO = new PatientDAO();
    private ObservableList<Patient> patientData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the table columns to use Patient properties
        resultName.setCellValueFactory(new PropertyValueFactory<>("name"));
        resultAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        resulthometown.setCellValueFactory(new PropertyValueFactory<>("hometown"));
        resultPhone.setCellValueFactory(new PropertyValueFactory<>("mobile"));

        // Bind the TableView to the data list
        tableView.setItems(patientData);

        // Set up search button action
        searchButton.setOnAction(event -> handleSearchPatient());
    }

    @FXML
    private void handleSearchPatient() {
        String searchName = searchField.getText();

        // Search for a patient by name using PatientDAO
        Patient patient = patientDAO.findPatient(searchName);

        // Display patient information or not found message
        if (patient != null) {
            // Clear previous data and add the found patient
            patientData.clear();
            patientData.add(patient);
            infoBox.setText("");  // Clear any previous "not found" message
        } else {
            // Show "not found" message if patient is not in the database
            infoBox.setText("Patient not found.");
            patientData.clear();
        }
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
