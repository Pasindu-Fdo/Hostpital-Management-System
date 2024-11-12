package com.example.login.medicine;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Phcreate {
    @FXML private Button createButton;
    @FXML private Button cancelButton;

    @FXML private TextField medicineid;
    @FXML private TextField medicinename;
    @FXML private TextField unitprice;
    @FXML private TextField mamount;
    @FXML private DatePicker expirydate;


    @FXML private Label errormessagelabel;

    private static final int ERROR_MESSAGE_DURATION = 5;
    public void addmedicine() {
        if (medicinename.getText().isEmpty() || unitprice.getText().isEmpty() || mamount.getText().isEmpty() || expirydate.getValue() == null) {
            setErrorWithTimer(errormessagelabel, "Please fill in all fields");
            return;
        }

        Connection connection = null;
        DBconnection dbUtils = new DBconnection();
        connection = dbUtils.getConnection();

        try {
            String mediName = medicinename.getText();
            double mediPrice = Double.parseDouble(unitprice.getText());
            int mediAmount = Integer.parseInt(mamount.getText());
            String mediExpiry = expirydate.getValue().toString();

            String query;
            PreparedStatement preparedStatement;

            if (medicineid.getText().isEmpty()) {
                query = "INSERT INTO medicine (name, price, quantity, expiredate) VALUES (?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, mediName);
                preparedStatement.setDouble(2, mediPrice);
                preparedStatement.setInt(3, mediAmount);
                preparedStatement.setString(4, mediExpiry);
            } else {
                int mediId = Integer.parseInt(medicineid.getText());
                query = "INSERT INTO medicine (mid, name, price, quantity, expiredate) VALUES (?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, mediId);
                preparedStatement.setString(2, mediName);
                preparedStatement.setDouble(3, mediPrice);
                preparedStatement.setInt(4, mediAmount);
                preparedStatement.setString(5, mediExpiry);
            }

            preparedStatement.executeUpdate();
            setErrorWithTimer(errormessagelabel, "Medicine Added Successfully");
            clearFields();

        } catch (NumberFormatException e) {
            setErrorWithTimer(errormessagelabel, "Invalid Values");
        } catch (Exception e) {
            e.printStackTrace();
            setErrorWithTimer(errormessagelabel, "An error occurred");
            clearFields();
        }
    }


    private void setErrorWithTimer(Label label, String message) {
        label.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MESSAGE_DURATION));
        pause.setOnFinished(e -> label.setText("")); // Clear the message after the timer
        pause.play();
    }

    public void clearFields() {
        medicineid.clear();
        medicinename.clear();
        unitprice.clear();
        mamount.clear();
        expirydate.setValue(null);
    }
    public void cancel() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("phdashboard.fxml"));
            Parent root = loader.load();


            Stage dcreateStage = new Stage();
            dcreateStage.setTitle("Dashboard");
            dcreateStage.setScene(new Scene(root));
            dcreateStage.show();

            Stage currentStage = (Stage) createButton.getScene().getWindow();


            if (currentStage != null) {
                currentStage.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
