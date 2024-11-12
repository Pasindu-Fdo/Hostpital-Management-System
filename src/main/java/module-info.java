module com.example.login {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.login.medicine;
    exports com.example.login;
    exports com.example.login.appoinment;

    exports com.example.login.demo1.Controllers;
    exports com.example.login.demo1.Models;
    exports com.example.login.demo1;
    exports com.example.login.demo1.services;
    exports com.example.login.demo1.utils;

    opens com.example.login.demo1.Controllers to javafx.fxml;
    opens com.example.login.demo1.Models to javafx.fxml;
    opens com.example.login.demo1 to javafx.fxml;
    opens com.example.login.demo1.services to javafx.fxml;
    opens com.example.login.demo1.utils to javafx.fxml;

    opens com.example.login.medicine to javafx.fxml;
    opens com.example.login to javafx.fxml;
    opens com.example.login.appoinment to javafx.fxml;

    exports com.example.login.Classes;


    opens com.example.login.Classes to javafx.fxml;


}