package com.example.login;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dview {

    public static String patientName;
    public static String doctorName;
    public static int patientId;
    public static int doctorId;
    public static String date;

    @FXML
    private TextField searchbar;
    @FXML
    private Button searchpbutton;
    @FXML
    private Button cancel;
    @FXML
    private TableView<Prescription> prescriptionTable;
    @FXML
    private TableColumn<Prescription, Integer> patientColumn;
    @FXML
    private TableColumn<Prescription, Integer> doctorColumn;
    @FXML
    private TableColumn<Prescription, String> dateColumn;
    @FXML
    private TableColumn<Prescription, String> medicineColumn;
    @FXML
    private TableColumn<Prescription, String> noteColumn;
    @FXML
    private TableColumn<Prescription, Button> updateColumn;
    @FXML
    private TableColumn<Prescription, Button> deleteColumn;
    @FXML
    private Label errorlabel;

    private ObservableList<Prescription> prescriptionList = FXCollections.observableArrayList();

    @FXML
    private void findprescription(ActionEvent event) {
        patientName = searchbar.getText();
        if (patientName.isEmpty()) {
            errorlabel.setText("Please enter a patient name.");
            return;
        }

        prescriptionList.clear();

        DBconnection dbUtils = new DBconnection();
        try (Connection conn = dbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT p.* FROM prescription p JOIN patient pa ON p.pid = pa.pid WHERE pa.name = ?"
             )) {
            stmt.setString(1, patientName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int pid = rs.getInt("pid");
                patientId = pid;
                int sid = rs.getInt("sid");
                doctorId = sid;
                date = rs.getString("date");
                String medicine = rs.getString("medicine");
                String note = rs.getString("note");

                Button updateButton = new Button("Update");
                updateButton.setOnAction(e -> openUpdateView(pid, date));

                Button deleteButton = new Button("Delete");
                deleteButton.setOnAction(e -> deleteRecord(pid, date));

                Prescription prescription = new Prescription(pid, sid, date, medicine, note);
                prescriptionList.add(prescription);
            }
            prescriptionTable.setItems(prescriptionList);

        } catch (SQLException e) {
            errorlabel.setText("Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void initialize() {
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("pid"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("sid"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        medicineColumn.setCellValueFactory(new PropertyValueFactory<>("medicine"));
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        updateColumn.setCellValueFactory(new PropertyValueFactory<>("updateAction"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteAction"));


        updateColumn.setCellFactory(param -> new TableCell<Prescription, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button updateButton = new Button("Update");
                    updateButton.setOnAction(e -> {
                        Prescription prescription = getTableRow().getItem();
                        if (prescription != null) {
                            openUpdateView(prescription.getPid(), prescription.getDate());
                        }
                    });
                    styleButton(updateButton);
                    updateButton.setStyle("-fx-text-fill: " + "BLUE" + ";");
                    updateButton.setOnMouseExited(event -> {
                        updateButton.setStyle("-fx-cursor: default;");
                        updateButton.setStyle("-fx-text-fill: " + "BLUE" + ";");
                    });
                    setGraphic(updateButton);
                }
            }
        });

        deleteColumn.setCellFactory(param -> new TableCell<Prescription, Button>() {
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button deleteButton = new Button("Delete");
                    deleteButton.setOnAction(e -> {
                        Prescription prescription = getTableRow().getItem();
                        if (prescription != null) {
                            deleteRecord(prescription.getPid(), prescription.getDate());
                        }
                    });

                    styleButton(deleteButton);
                    deleteButton.setStyle("-fx-text-fill: " + "RED" + ";");
                    deleteButton.setOnMouseExited(event -> {
                        deleteButton.setStyle("-fx-cursor: default;");
                        deleteButton.setStyle("-fx-text-fill: " + "RED" + ";");
                    });
                    setGraphic(deleteButton);
                }
            }
        });
    }


    private void openUpdateView(int pid, String date) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dupdate.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(pane));
            stage.setTitle("Update Prescription");
            stage.show();
        } catch (Exception e) {
            errorlabel.setText("Unable to open update view: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void deleteRecord(int pid, String date) {
        DBconnection dbUtils = new DBconnection();
        try (Connection conn = dbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM prescription WHERE pid = ? AND date = ?")) {
            stmt.setInt(1, pid);
            stmt.setString(2, date);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                errorlabel.setText("Record deleted successfully.");
                prescriptionList.removeIf(p -> p.getPid() == pid && p.getDate().equals(date));
            }
        } catch (SQLException e) {
            errorlabel.setText("Error deleting record: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cancelaction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ddashboard.fxml"));
            Parent root = loader.load();

            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Doctor Dashboard");
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
    private void styleButton(Button button) {

        button.setOnMouseEntered(event -> {
            button.setStyle("-fx-cursor: hand;");
        });
        button.setOnMouseExited(event -> {
            button.setStyle("-fx-cursor: default;");

        });

    }


}
