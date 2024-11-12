package com.example.login.medicine;
import com.example.login.medicine.Medicine;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;

public class Phview {

    @FXML
    private TableView<Medicine> medicineTable;

    @FXML
    private TableColumn<Medicine, Integer> columnMedicineID;

    @FXML
    private TableColumn<Medicine, String> columnName;

    @FXML
    private TableColumn<Medicine, Integer> columnAmount;

    @FXML
    private TableColumn<Medicine, Double> columnPrice;

    @FXML
    private TableColumn<Medicine, String> columnExpiryDate;

    @FXML
    private Button refreshButton;

    @FXML
    private Button cancel;

    @FXML
    private Label errorLabel;

    private ObservableList<Medicine> medicineList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns with PropertyValueFactory
        columnMedicineID.setCellValueFactory(new PropertyValueFactory<>("medicineID"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        columnExpiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        // Load medicine data when the window opens
        loadMedicineData();

        // Set up refresh button action to reload the data
        refreshButton.setOnAction(event -> loadMedicineData());
    }

    private void loadMedicineData() {
        // Clear any existing data in the table
        medicineList.clear();

        // Fetch data from the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medflow", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM medicine")) {

            while (resultSet.next()) {
                int medicineID = resultSet.getInt("mid");
                String name = resultSet.getString("name");
                int amount = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                String expiryDate = resultSet.getString("expiredate");

                // Add the medicine to the list
                Medicine medicine = new Medicine(medicineID, name, amount, price, expiryDate);
                medicineList.add(medicine);
            }

            // Set the table's items to the list
            medicineTable.setItems(medicineList);
        } catch (SQLException e) {
            // Handle SQL exceptions and show an error message
            errorLabel.setText("Error loading data: " + e.getMessage());
        }
    }

    public void cancel() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("phdashboard.fxml"));
            Parent root = loader.load();


            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Dashboard");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) cancel.getScene().getWindow();


            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
