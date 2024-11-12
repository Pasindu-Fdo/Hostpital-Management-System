package com.example.login.medicine;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnection {
    public Connection dblink;

    public Connection getConnection() {
        String dbName = "medflow";
        String user = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dblink = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dblink;
    }
}
