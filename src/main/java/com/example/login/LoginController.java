package com.example.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.sql.ResultSet.*;


public class LoginController {

    public static Integer userID;
    public static String userName;
    public static String userRole;

    @FXML
    private Button cancel;

    @FXML
    private Button login;

    @FXML
    private Label errorlabel;
    @FXML
    private TextField username;
    @FXML
    private TextField password;


    public void loginaction(ActionEvent event) {


        if(username.getText().isBlank() == false && password.getText().isBlank()== false) {
            //errorlabel.setText("Login Successful");
            validateLogin();
        }else{
            errorlabel.setText("Please enter a username and password.");
        }
    }

    public void cancelaction(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void validateLogin() {
        DBconnection nowConnect = new DBconnection();
        Connection connectDB = nowConnect.getConnection();

        String verifyLogin = "SELECT count(1) FROM staff WHERE name = '"+username.getText()+"' AND password = '"+password.getText()+"'";

        try (Statement statement = connectDB.createStatement()) {
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            if (queryResult.next() && queryResult.getInt(1) == 1) {
                String checkLogin = "SELECT * FROM staff WHERE name = '" + username.getText() + "' AND password = '" + password.getText() + "'";

                try (Statement checkstatement = connectDB.createStatement()) {
                    ResultSet qqueryResult = checkstatement.executeQuery(checkLogin);
                    if (qqueryResult.next()) {
                        userID = qqueryResult.getInt("sid");
                        userName = qqueryResult.getString("name");
                        userRole = qqueryResult.getString("jobtitle");

                        if (userRole.equals("Doctor")) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ddashboard.fxml"));
                                Parent root = loader.load();

                                Stage dcreateStage = new Stage();
                                dcreateStage.setTitle("Doctor Dashboard");
                                dcreateStage.setScene(new Scene(root));
                                dcreateStage.show();

                                Stage currentStage = (Stage) login.getScene().getWindow();
                                if (currentStage != null) {
                                    currentStage.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (userRole.equals("Pharmacist")) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/medicine/phdashboard.fxml"));
                                Parent root = loader.load();

                                Stage dcreateStage = new Stage();
                                dcreateStage.setTitle("Dashboard");
                                dcreateStage.setScene(new Scene(root));
                                dcreateStage.show();

                                Stage currentStage = (Stage) login.getScene().getWindow();
                                if (currentStage != null) {
                                    currentStage.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (userRole.equals("Receptionist")) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("mainrdash.fxml"));
                                Parent root = loader.load();

                                Stage dcreateStage = new Stage();
                                dcreateStage.setTitle("Receptionist Dashboard");
                                dcreateStage.setScene(new Scene(root));
                                dcreateStage.show();

                                Stage currentStage = (Stage) login.getScene().getWindow();
                                if (currentStage != null) {
                                    currentStage.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (userRole.equals("Admin")) {
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/login/fxml/AdminDashboard.fxml"));
                                Parent root = loader.load();

                                Stage dcreateStage = new Stage();
                                dcreateStage.setTitle("Dashboard");
                                dcreateStage.setScene(new Scene(root));
                                dcreateStage.show();

                                Stage currentStage = (Stage) login.getScene().getWindow();
                                if (currentStage != null) {
                                    currentStage.close();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                errorlabel.setText("Invalid login. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
