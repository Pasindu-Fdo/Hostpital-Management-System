package com.example.login.Classes;

import java.sql.*;

public class PatientDAO {
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patient (name, age, hometown, mobile) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getAge());
            pstmt.setString(3, patient.gethometown());
            pstmt.setString(4, patient.getTelephone());
            pstmt.executeUpdate();

            sql = "SELECT * FROM patient WHERE mobile = ?";
            PreparedStatement pstmtt = conn.prepareStatement(sql);
            pstmtt.setString(1, patient.getTelephone());
            ResultSet rs = pstmtt.executeQuery();
            if (rs.next()) {
                AddPatientController.pid = (STR."Patient's ID is: \{rs.getString("pid")}");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patient findPatient(String name) {
        String sql = "SELECT * FROM patient WHERE name = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Patient(
                        rs.getString("name"),
                        rs.getString("age"),
                        rs.getString("hometown"),
                        rs.getString("mobile")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Patient1 findPatient1(String name) {
        String sql = "SELECT * FROM patient WHERE name = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Patient1(
                        rs.getInt("pid"),
                        rs.getString("name"),
                        rs.getString("age"),
                        rs.getString("hometown"),
                        rs.getString("mobile")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePatient(Patient1 patient, String oldName) {
        String sql = "UPDATE patient SET name = ?, age = ?, hometown = ?, mobile = ? WHERE name = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getAge());
            pstmt.setString(3, patient.gethometown());
            pstmt.setString(4, patient.getTelephone());
            pstmt.setString(5, oldName);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatient(String name) {
        String sql = "DELETE FROM patient WHERE name = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

