package com.example.login.demo1.utils;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
public class Utils {
    public static void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean isValidEmail(String email) {
        return !email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}