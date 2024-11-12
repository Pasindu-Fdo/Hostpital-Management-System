package com.example.login.demo1.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.example.login.demo1.Models.User;
import com.example.login.demo1.services.DatabaseService;

public class AdminDashboardController {
    @FXML private Button addUserBtn;
    @FXML private Button deleteUserBtn;
    @FXML private Button viewUsersBtn;
    @FXML private Button updateUserBtn;
    @FXML private TableView<User> usersTable;
    @FXML private Label statusLabel;

    @FXML private TableColumn<User, Integer> sidColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> mobileColumn;
    @FXML private TableColumn<User, String> addressColumn;
    @FXML private TableColumn<User, String> jobtitleColumn;


    @FXML
    public void initialize() {
        setupButtonActions();
        configureTableColumns(); // Configure columns
        loadUsers(); // Load users after columns are configured
    }

    private void configureTableColumns() {
        sidColumn.setCellValueFactory(new PropertyValueFactory<>("sid"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        jobtitleColumn.setCellValueFactory(new PropertyValueFactory<>("jobtitle"));
    }


    private void setupButtonActions() {
        addUserBtn.setOnAction(e -> openAddUserWindow());
        deleteUserBtn.setOnAction(e -> openDeleteUserWindow());
        viewUsersBtn.setOnAction(e -> openViewUsersWindow());
        updateUserBtn.setOnAction(e -> openUpdateUserWindow());
    }

    private void openAddUserWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxml/AddUser.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New User");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (Exception e) {
            showError("Error opening Add User window: " + e.getMessage());
            e.printStackTrace(); // This will print the stack trace to the console for debugging
        }
    }

    private void openDeleteUserWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxml/DeleteUser.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Delete User");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showError("Error opening Delete User window");
        }
    }

    private void openViewUsersWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxml/ViewUsers.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("View Users");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showError("Error opening View Users window");
        }
    }

    private void openUpdateUserWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxml/UpdateUser.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Update User");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            showError("Error opening Update User window");
        }
    }

    private void loadUsers() {
        try {
            usersTable.getItems().setAll(DatabaseService.getAllUsers());
        } catch (Exception e) {
            showError("Error loading users");
        }
    }

    private void showError(String message) {
        statusLabel.setText(message);
    }
}