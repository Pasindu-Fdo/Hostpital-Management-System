package com.example.login.medicine;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;

import java.sql.*;

public class Phremove {

    @FXML
    private TextField medicine_name;
    @FXML
    private TextField medicine_id;
    @FXML
    private VBox matchdata;
    @FXML
    private Label messagelabel;
    @FXML
    private Button button_remove;
    @FXML
    private Button findbyname;
    @FXML
    private Button findbyid;

    @FXML
    private Button cancel;

    private Connection connection;

    @FXML
    public void initialize() {
        // Establish database connection
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/medflow", "root", "");
        } catch (SQLException e) {
            messagelabel.setText("Database connection failed: " + e.getMessage());
        }

        // Search by Name Button Action
        findbyname.setOnAction(event -> findByName());

        // Search by ID Button Action
        findbyid.setOnAction(event -> findByID());

        // Remove Button Action
        button_remove.setOnAction(event -> removeMedicine());
    }

    private void findByName() {
        String name = medicine_name.getText().trim();
        if (name.isEmpty()) {
            messagelabel.setText("Please enter a medicine name.");
            return;
        }

        // Clear previous data
        matchdata.getChildren().clear();
        messagelabel.setText("");

        // Fetch matching medicine by name
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM medicine WHERE name = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                displayMedicine(rs);
            }

            if (matchdata.getChildren().isEmpty()) {
                messagelabel.setText("No matching medicines found.");
            }
        } catch (SQLException e) {
            messagelabel.setText("Error searching by name: " + e.getMessage());
        }
    }

    private void findByID() {
        String idText = medicine_id.getText().trim();
        if (idText.isEmpty()) {
            messagelabel.setText("Please enter a medicine ID.");
            return;
        }

        // Clear previous data
        matchdata.getChildren().clear();
        messagelabel.setText("");

        try {
            int id = Integer.parseInt(idText);
            // Fetch matching medicine by ID
            try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM medicine WHERE mid = ?")) {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    displayMedicine(rs);
                }

                if (matchdata.getChildren().isEmpty()) {
                    messagelabel.setText("No matching medicines found.");
                }
            }
        } catch (NumberFormatException e) {
            messagelabel.setText("Invalid medicine ID format.");
        } catch (SQLException e) {
            messagelabel.setText("Error searching by ID: " + e.getMessage());
        }
    }

    private void displayMedicine(ResultSet rs) throws SQLException {
        // Create text elements to display medicine details
        String medicineDetails = "ID: " + rs.getInt("mid") + " | Name: " + rs.getString("name") +
                " | Amount: " + rs.getInt("quantity") + " | Price: " + rs.getDouble("price") +
                " | Expiry Date: " + rs.getString("expiredate");

        Text medicineText = new Text(medicineDetails);
        medicineText.setOnMouseClicked(event -> selectMedicineToDelete(rs));
        matchdata.getChildren().add(medicineText);
    }

    private void selectMedicineToDelete(ResultSet rs) {
        try {
            int medicineID = rs.getInt("mid");
            button_remove.setOnAction(event -> removeMedicineByID(medicineID));
            messagelabel.setText("Selected medicine ID: " + medicineID);
        } catch (SQLException e) {
            messagelabel.setText("Error selecting medicine: " + e.getMessage());
        }
    }

    private void removeMedicineByID(int medicineID) {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM medicine WHERE mid = ?")) {
            stmt.setInt(1, medicineID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                messagelabel.setText("Medicine removed successfully.");
                matchdata.getChildren().clear();
            } else {
                messagelabel.setText("Error: Medicine not found.");
            }
        } catch (SQLException e) {
            messagelabel.setText("Error deleting medicine: " + e.getMessage());
        }
    }

    private void removeMedicine() {
        String idText = medicine_id.getText().trim();
        if (idText.isEmpty()) {
            messagelabel.setText("Please enter a medicine ID to remove.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            removeMedicineByID(id);
        } catch (NumberFormatException e) {
            messagelabel.setText("Invalid medicine ID format.");
        }
    }
    public void cancel() {
        try {
            // Load the new FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("phdashboard.fxml"));
            Parent root = loader.load();

            // Create and set up the new stage
            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Dashboard");
            dcreateStage.setScene(new Scene(root));

            // Get the current stage and close it
            Stage currentStage = (Stage) cancel.getScene().getWindow();
            if (currentStage != null) {
                currentStage.close();
            }

            // Show the new stage
            dcreateStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
