package com.example.login.demo1.Controllers;

//import com.sun.jdi.Value;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.login.demo1.Models.User;
import com.example.login.demo1.services.DatabaseService;
import com.example.login.demo1.utils.Utils;
import javafx.stage.Stage;

public class UpdateUserController {
    @FXML private TextField searchUpdateField;
    @FXML private TextField updateUsernameField;
    @FXML private TextField  updateMobileField;
    @FXML private PasswordField updatePasswordField;
    @FXML private ComboBox<String> updateRoleComboBox;
    @FXML private Button updateButton;
    @FXML private Label updateMessageLabel;
    @FXML private TextField updateAddressField;
    @FXML private Button searchUpdateButton;
    @FXML private Button cancelUpdateButton;


    private int currentUserId = -1; // Field to store current user's SID

    @FXML
    public void initialize() {
        setupComboBoxes();
        setupButtons();
        setupSearch();
    }

    private void setupComboBoxes() {
        updateRoleComboBox.getItems().addAll("Doctor", "Receptionist", "Nurse");
    }

    private void setupButtons() {
        updateButton.setOnAction(e -> updateUser());
        searchUpdateButton.setOnAction(e -> loadUserDetails(searchUpdateField.getText()));
        cancelUpdateButton.setOnAction(e -> closeWindow());
    }

    private void setupSearch() {
        searchUpdateField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadUserDetails(newValue);
        });
    }

    private void loadUserDetails(String searchTerm) {
        User user = DatabaseService.getUserByUsername(searchTerm);
        if (user != null) {
            currentUserId = user.getSid(); // Set current user's SID
            updateUsernameField.setText(user.getName());
            updateMobileField.setText(String.valueOf(user.getMobile()));
            updateAddressField.setText(user.getAddress());
            updatePasswordField.setText(user.getPassword());
            updateRoleComboBox.setValue(user.getJobtitle());
        } else {
            resetFields();
            currentUserId = -1; // Reset SID if user not found
        }
    }

    private void updateUser() {
        if (validateInputs() && currentUserId != -1) { // Ensure a valid user is loaded

            int mobileNumber = Integer.parseInt(updateMobileField.getText().trim());

            User updatedUser = new User(
                    updateUsernameField.getText(),
                    mobileNumber,
                    updateAddressField.getText(),
                    updatePasswordField.getText(),
                    updateRoleComboBox.getValue()
            );
            updatedUser.setSid(currentUserId); // Set the SID for accurate update

            if (DatabaseService.updateUser(updatedUser)) {
                showMessage("User updated successfully", false);
                resetFields();
            } else {
                showMessage("Error updating user", true);
            }
        } else {
            showMessage("No user selected or input is invalid", true);
        }
    }

    private boolean validateInputs() {
        if (updateUsernameField.getText().isEmpty()) {
            showMessage("Please fill in all fields", true);
            return false;
        }
        return true;
    }

    private void resetFields() {
        updateUsernameField.clear();
        updateMobileField.clear();
        updateAddressField.clear();
        updateRoleComboBox.setValue(null);
        updatePasswordField.clear();
        currentUserId = -1; // Reset SID when fields are cleared
    }

    private void showMessage(String message, boolean isError) {
        updateMessageLabel.setText(message);
        updateMessageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
    private void closeWindow() {
        Stage stage = (Stage) cancelUpdateButton.getScene().getWindow(); // Get the stage from the button
        stage.close(); // Close the window
    }
}
