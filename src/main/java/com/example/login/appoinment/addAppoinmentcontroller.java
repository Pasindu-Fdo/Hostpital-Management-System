package com.example.login.appoinment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.DatePicker;
import com.example.login.appoinment.DButils;

import java.sql.*;

public class addAppoinmentcontroller {
    @FXML
    private Button ACadd;
    @FXML
    private Button ABPsearch;
    @FXML
    private Button ABDsearch;
    @FXML
    private TextField Pname;
    @FXML
    private TextField Dname;
    @FXML
    private Label Perrormsgid;
    @FXML
    private Label Derrormsgid;
    @FXML
    private Label Aerrormsgid;
    @FXML
    private Label Asuccmsgid;
    @FXML
    private DatePicker Adate;
    @FXML
    private TextField Atime;
    @FXML
    private TextField Areason;

    private static final int ERROR_MESSAGE_DURATION = 5;

    public void PsearchButtonAction(ActionEvent event) {
        try {
            if (Pname.getText().isEmpty()) {
                setErrorWithTimer(Perrormsgid, "Enter Patient name");
            } else {
                String patientName = Pname.getText();
                if (!patientName.matches("[a-zA-Z\\s]+")) {
                    setErrorWithTimer(Perrormsgid, "Enter valid name");
                }else{
                    Connection connection = null;
                    DButils dbUtils = new DButils();
                    connection = dbUtils.getConnection();

                    String sql="SELECT * FROM patient WHERE name=?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, patientName);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        Perrormsgid.setStyle("-fx-text-fill: " + "Blue" + ";");
                        setErrorWithTimer(Perrormsgid, "Patient Record Available");
                    }else{
                        Perrormsgid.setStyle("-fx-text-fill: " + "Red" + ";");
                        setErrorWithTimer(Perrormsgid, "Patient Record Not Available");
                        clearPname();
                    }
                }
            }
        } catch (Exception e) {
            setErrorWithTimer(Perrormsgid, "Unexpected error occurred.");
            e.printStackTrace();
        }
    }

    public void DsearchButtonAction(ActionEvent event) {
        try {
            if (Dname.getText().isEmpty()) {
                setErrorWithTimer(Derrormsgid, "Enter Doctor Name");
            } else {
                String doctorName = Dname.getText();
                if (!doctorName.matches("[a-zA-Z\\s]+")) {
                    setErrorWithTimer(Derrormsgid, "Enter valid name");
                }else{
                    Connection connection = null;
                    DButils dbUtils = new DButils();
                    connection = dbUtils.getConnection();

                    String sql="SELECT * FROM staff WHERE name=? AND jobtitle='Doctor'";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, doctorName);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        Derrormsgid.setStyle("-fx-text-fill: " + "Blue" + ";");
                        setErrorWithTimer(Derrormsgid, "Doctor Available");
                    }else{
                        Derrormsgid.setStyle("-fx-text-fill: " + "Red" + ";");
                        setErrorWithTimer(Derrormsgid, "Doctor Not Available");
                        clearDname();
                    }
                }
            }
        } catch (Exception e) {
            setErrorWithTimer(Derrormsgid,e.getMessage());
            e.printStackTrace();
        }
    }

    public void addAppoinment(ActionEvent event) {
        Connection connection = null; // Declare connection here
        try {
            DButils dbUtils = new DButils(); // Create an instance of DButils
            connection = dbUtils.getConnection();

            if (Pname.getText().isEmpty() && Dname.getText().isEmpty() && (Adate.getValue() == null) && Atime.getText().isEmpty() && Areason.getText().isEmpty()) {
                setErrorWithTimer(Aerrormsgid, "Please Enter the information");
            } else {
                if (Pname.getText().isEmpty()) {
                    setErrorWithTimer(Aerrormsgid, "Enter Patient name");
                } else if (Dname.getText().isEmpty()) {
                    setErrorWithTimer(Aerrormsgid, "Enter Doctor name");
                } else if (Adate.getValue() == null) {
                    setErrorWithTimer(Aerrormsgid, "Select Date");
                } else if (Atime.getText().isEmpty()) {
                    setErrorWithTimer(Aerrormsgid, "Enter Time");
                } else if (Areason.getText().isEmpty()) {
                    setErrorWithTimer(Aerrormsgid, "Enter Reason");
                } else {

                    String Dsql="SELECT sid FROM staff WHERE name=? AND jobtitle='Doctor'";
                    PreparedStatement Dpst = connection.prepareStatement(Dsql);
                    Dpst.setString(1, Dname.getText());
                    ResultSet Drs = Dpst.executeQuery();

                    int Did=0;
                    int Pid=0;

                    while (Drs.next()) {
                         Did = Drs.getInt("sid");
                    }

                    String Psql="SELECT pid FROM patient WHERE name=?";
                    PreparedStatement Ppst = connection.prepareStatement(Psql);
                    Ppst.setString(1, Pname.getText());
                    ResultSet Prs = Ppst.executeQuery();

                    while (Prs.next()) {
                        Pid = Prs.getInt("pid");
                    }


                    String sql="INSERT INTO appointment VALUES (?,?,?,?,?,?)";
                    PreparedStatement pst=connection.prepareStatement(sql);
                    pst.setString(1, String.valueOf(Did));
                    pst.setString(2, String.valueOf(Pid));
                    pst.setString(3, String.valueOf(Adate.getValue()));
                    pst.setString(4, String.valueOf(Atime.getText()));
                    pst.setString(5, String.valueOf(Areason.getText()));
                    pst.setString(6, "No");
                    pst.executeUpdate();

                    setErrorWithTimer(Asuccmsgid,"New Appointment Added");
                    clearInputFields();
                }
            }
        } catch (SQLException e) {
            setErrorWithTimer(Aerrormsgid, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            setErrorWithTimer(Aerrormsgid, e.getMessage());
            e.printStackTrace();
        }
    }

    public void cancleButtonAction(ActionEvent event) {
        try {
            Stage stage = (Stage) ACadd.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.err.println("Error closing the window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setErrorWithTimer(Label label, String message) {
        label.setText(message);
        PauseTransition pause = new PauseTransition(Duration.seconds(ERROR_MESSAGE_DURATION));
        pause.setOnFinished(e -> label.setText("")); // Clear the message after the timer
        pause.play();
    }

    private void clearInputFields() {
        Pname.clear();
        Dname.clear();
        Adate.setValue(null);
        Atime.clear();
        Areason.clear();
    }

    private void clearPname() {
        Pname.clear();
    }

    private void clearDname() {
        Dname.clear();
    }

}
