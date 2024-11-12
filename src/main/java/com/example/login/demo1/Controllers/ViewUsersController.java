package com.example.login.demo1.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.login.demo1.Models.User;
import com.example.login.demo1.services.DatabaseService;
import com.example.login.demo1.utils.Utils;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ViewUsersController {
    @FXML private TextField filterField;
    @FXML private ComboBox<String> filterRoleComboBox;
    @FXML private TableView<User> usersTableView;
    @FXML private TableColumn<User, Integer> idColumnView;    // Correct type parameter
    @FXML private TableColumn<User, String> nameColumnView;   // Correct type parameter
    @FXML private TableColumn<User, String> mobileColumnView;  // Correct type parameter
    @FXML private TableColumn<User, String> addressColumnView;   // Correct type parameter
    @FXML private TableColumn<User, String> titleColumnView; // Correct type parameter
    @FXML private Button refreshButton;
    @FXML private Button closeViewButton;

    @FXML
    public void initialize() {
        setupFilters();
        setupButtons();
        setupTableColumns();   // Call to set up table columns
        loadUsers();
    }

    private void setupTableColumns() {
        // Set the cell value factories for each column
        idColumnView.setCellValueFactory(new PropertyValueFactory<>("sid"));
        nameColumnView.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileColumnView.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        addressColumnView.setCellValueFactory(new PropertyValueFactory<>("address"));
        titleColumnView.setCellValueFactory(new PropertyValueFactory<>("jobtitle"));
    }

    private void setupFilters() {
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterUsers();
        });

        filterRoleComboBox.getItems().addAll("Doctor", "Receptionist", "Nurse", "Manager","Admin");
        filterRoleComboBox.setValue("All");
        filterRoleComboBox.setOnAction(e -> filterUsers());
    }

    private void setupButtons() {
        refreshButton.setOnAction(e -> loadUsers());
        closeViewButton.setOnAction(e -> handleCloseButtonAction());
    }

    private void filterUsers() {
        String searchTerm = filterField.getText();
        String selectedRole = filterRoleComboBox.getValue();

        if (selectedRole.equals("All")) {
            usersTableView.getItems().setAll(DatabaseService.searchUsers(searchTerm));
        } else {
            usersTableView.getItems().setAll(DatabaseService.searchUsersByRole(searchTerm, selectedRole));
        }
    }

    private void loadUsers() {
        usersTableView.getItems().setAll(DatabaseService.getAllUsers());
    }

    private void handleCloseButtonAction() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) closeViewButton.getScene().getWindow();
        stage.close();
    }


}