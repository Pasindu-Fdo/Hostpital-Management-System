package com.example.login;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Dupdatecontroller {
    @FXML
    private Button cancel;

    @FXML
    private Button pupdate;

    @FXML
    private Label errorlabel;

    @FXML
    private Label dnamelabel;

    @FXML
    private Label pnamelabel;

    @FXML
    private Label pdate;

    @FXML
    private TextArea pmedicine;

    @FXML
    private TextArea pnote;

    private static final int ERROR_MESSAGE_DURATION = 5;

    String dpatientname = Dview.patientName;
    String ddoctorname;
    int dpatientid = Dview.patientId;
    int ddoctorid = Dview.doctorId;
    public String date = Dview.date;


    public void initialize() {
        DBconnection dbconnection = new DBconnection();
        Connection connection = dbconnection.getConnection();
        String sql = "SELECT * FROM staff WHERE sid=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ddoctorid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ddoctorname = resultSet.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();}
        dnamelabel.setText(ddoctorname);
        pnamelabel.setText(dpatientname);
        pdate.setText(date);
    }

    public void updatepresscription() {
        try {
            if (pmedicine.getText().isEmpty()) {
                setErrorWithTimer(errorlabel, "Enter all fields");
            } else {
                String medicine = pmedicine.getText();
                String note = pnote.getText();
                Connection connection = null;
                DBconnection dbUtils = new DBconnection();
                connection = dbUtils.getConnection();

                String sql = "UPDATE prescription SET medicine=?, note=? WHERE date=?AND pid=? AND sid=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, medicine);
                preparedStatement.setString(2, note);
                preparedStatement.setString(3, date);
                preparedStatement.setInt(4, dpatientid);
                preparedStatement.setInt(5, ddoctorid);
                preparedStatement.executeUpdate();
                setErrorWithTimer(errorlabel, "Prescription updated successfully");
            }
        } catch (Exception e) {
            setErrorWithTimer(errorlabel, "Error updating prescription");
        }
    }

    public void cancelaction(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private void setErrorWithTimer(Label label, String message) {
        label.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MESSAGE_DURATION));
        pause.setOnFinished(e -> label.setText(""));
        pause.play();
    }

}
