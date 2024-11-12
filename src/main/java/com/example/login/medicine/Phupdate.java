package com.example.login.medicine;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.sql.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Phupdate {

    @FXML
    private TextField mediID, newname, newamount, newprice;
    @FXML
    private DatePicker newdate;
    @FXML
    private Button findbyid, updatebutton, cancel;
    @FXML
    private Label oldid, oldname, oldamount, oldprice, oldexdate, error, iderror;

    private Connection connection;

    // Method to initialize database connection
    private void connectDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/medflow"; // Update with your database details
        String user = "root";
        String password = "";
        connection = DriverManager.getConnection(url, user, password);
    }

    // Find medicine by ID
    @FXML
    public void findMedicineById() {
        String id = mediID.getText().trim();

        if (id.isEmpty()) {
            iderror.setText("Please enter a medicine ID.");
            return;
        }

        try {
            connectDatabase();
            String query = "SELECT * FROM medicine WHERE mid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                oldid.setText(resultSet.getString("mid"));
                oldname.setText(resultSet.getString("name"));
                oldamount.setText(resultSet.getString("quantity"));
                oldprice.setText(resultSet.getString("price"));
                oldexdate.setText(resultSet.getString("expiredate"));
                iderror.setText(""); // Clear any previous errors
            } else {
                iderror.setText("No medicine found with this ID.");
                clearOldData(); // Clear the old data fields
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            iderror.setText("Database error: " + e.getMessage());
        }
    }

    // Update medicine data
    @FXML
    public void updateMedicine() {
        String newName = newname.getText().trim();
        String newAmount = newamount.getText().trim();
        String newPrice = newprice.getText(); // Assuming price is not editable
        String newExpireDate = newdate.getValue() != null ? newdate.getValue().toString() : "";

        // Validation checks
        if (newName.isEmpty() || newAmount.isEmpty() || newExpireDate.isEmpty()) {
            error.setText("Please fill in all the fields.");
            return;
        }

        if (!isValidInteger(newAmount)) {
            error.setText("Please enter a valid number for quantity.");
            clearFields();
            return;
        }

        // Proceed with update logic if validation passes
        try {
            connectDatabase();
            String query = "UPDATE medicine SET name = ?, quantity = ?, price = ?, expiredate = ? WHERE mid = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, newAmount);
            statement.setString(3, newPrice);
            statement.setString(4, newExpireDate);
            statement.setString(5, oldid.getText());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                error.setText("Medicine data updated successfully!");
                clearOldData();
                clearFields();
            } else {
                error.setText("Update failed. Please try again.");
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            error.setText("Database error: " + e.getMessage());
        }
    }

    // Helper method to validate if a string is a valid integer
    private boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Clear the old data fields
    private void clearOldData() {
        oldid.setText("");
        oldname.setText("");
        oldamount.setText("");
        oldprice.setText("");
        oldexdate.setText("");
    }

    // Clear input fields
    private void clearFields() {
        newname.clear();
        newamount.clear();
        newdate.setValue(null);
        newprice.clear();
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
