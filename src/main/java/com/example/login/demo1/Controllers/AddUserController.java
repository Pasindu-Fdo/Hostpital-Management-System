package com.example.login.demo1.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.login.demo1.Models.User;
import com.example.login.demo1.services.DatabaseService;
import com.example.login.demo1.utils.Utils;

public class AddUserController {
    @FXML private TextField usernameField;
    @FXML private TextField mobilenumberField;
    @FXML private TextField AddressField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        setupComboBoxes();
        setupButtons();
    }

    private void setupComboBoxes() {
        roleComboBox.getItems().addAll("Doctor", "Receptionist", "Nurse");
    }

    private void setupButtons() {
        saveButton.setOnAction(e -> saveUser());
        cancelButton.setOnAction(e -> closeWindow());
    }

    private void saveUser() {
        if (validateInputs()) {
            int mobileNumber = Integer.parseInt(mobilenumberField.getText().trim());
            User user = new User(
                    usernameField.getText(),
                    mobileNumber,
                    AddressField.getText(),
                    roleComboBox.getValue(),
                    passwordField.getText()
            );

            if (DatabaseService.saveUser(user)) {
                showMessage("User saved successfully", false);
                closeWindow();
            } else {
                showMessage("Error saving user", true);
            }
        }
    }

    private boolean validateInputs() {
        if (usernameField.getText().isEmpty() || mobilenumberField.getText().isEmpty() ||
                AddressField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            showMessage("Please fill in all fields", true);
            return false;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showMessage("Passwords do not match", true);
            return false;
        }

        return true;
    }

    private void showMessage(String message, boolean isError) {
        messageLabel.setText(message);
        messageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }

    private void closeWindow() {
        saveButton.getScene().getWindow().hide();
    }
}