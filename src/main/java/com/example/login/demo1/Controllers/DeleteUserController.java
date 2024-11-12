package com.example.login.demo1.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.login.demo1.Models.User;
import com.example.login.demo1.services.DatabaseService;
import com.example.login.demo1.utils.Utils;
import javafx.scene.control.cell.PropertyValueFactory;

public class DeleteUserController {
    @FXML private TextField searchField;
    @FXML private TableView<User> usersTableDelete;
    @FXML private Button deleteButton;
    @FXML private Button cancelDeleteButton;
    @FXML private Label deleteMessageLabel;
    @FXML private TableColumn<User, Integer> idColumnDelete;
    @FXML private TableColumn<User, String> nameColumnDelete;
    @FXML private TableColumn<User, String> mobileColumnDelete;
    @FXML private TableColumn<User, String> addressColumnDelete;
    @FXML private TableColumn<User, String> jobtitleColumnDelete;

    @FXML
    public void initialize() {
        setupSearch();
        setupButtons();
        loadUsers();
        setupTableColumns();
    }

    private void setupTableColumns() {
        // Set the cell value factories for each column
        idColumnDelete.setCellValueFactory(new PropertyValueFactory<>("sid"));
        nameColumnDelete.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileColumnDelete.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        addressColumnDelete.setCellValueFactory(new PropertyValueFactory<>("address"));
        jobtitleColumnDelete.setCellValueFactory(new PropertyValueFactory<>("jobtitle"));
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterUsers(newValue);
        });
    }

    private void setupButtons() {
        deleteButton.setOnAction(e -> deleteSelectedUser());
        cancelDeleteButton.setOnAction(e -> closeWindow());
    }

    private void deleteSelectedUser() {
        User selectedUser = usersTableDelete.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            if (showConfirmation()) {
                if (DatabaseService.deleteUser(selectedUser.getSid())) {
                    showMessage("User deleted successfully", false);
                    loadUsers();
                } else {
                    showMessage("Error deleting user", true);
                }
            }
        } else {
            showMessage("Please select a user to delete", true);
        }
    }

    private boolean showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setContentText("Are you sure you want to delete the selected user?");
        return alert.showAndWait().map(response -> response == ButtonType.OK).orElse(false);
    }

    private void filterUsers(String searchTerm) {
        usersTableDelete.getItems().setAll(DatabaseService.searchUsers(searchTerm));
    }

    private void loadUsers() {
        usersTableDelete.getItems().setAll(DatabaseService.getAllUsers());
    }

    private void showMessage(String message, boolean isError) {
        deleteMessageLabel.setText(message);
        deleteMessageLabel.setStyle(isError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }

    private void closeWindow() {
        deleteButton.getScene().getWindow().hide();
    }
}