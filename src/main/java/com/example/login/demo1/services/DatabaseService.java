package com.example.login.demo1.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.login.demo1.Models.User;
public class DatabaseService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/medflow";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static boolean saveUser(User user) {
        String sql = "INSERT INTO staff (name, mobile, address, jobtitle, password) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getMobile());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getJobtitle());
            pstmt.setString(5, user.getPassword());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("name"),
                        rs.getInt("mobile"),
                        rs.getString("address"),
                        rs.getString("jobtitle"),
                        rs.getString("password")
                );
                user.setSid(rs.getInt("sid"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static boolean deleteUser(int userId) {
        String sql = "DELETE FROM staff WHERE sid = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<User> searchUsers(String searchTerm) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE name LIKE ? OR sid LIKE ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchTerm + "%");
            pstmt.setString(2, "%" + searchTerm + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getString("name"),
                            rs.getInt("mobile"),
                            rs.getString("address"),
                            rs.getString("jobtitle"),
                            rs.getString("password")
                    );
                    user.setSid(rs.getInt("sid"));

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<User> searchUsersByRole(String searchTerm, String role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE (name LIKE ? ) AND jobtitle = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + searchTerm + "%");
            pstmt.setString(2, "%" + searchTerm + "%");
            //pstmt.setString(3, role);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getString("name"),
                            rs.getInt("mobile"),
                            rs.getString("address"),
                            rs.getString("jobtitle"),
                            rs.getString("password")
                    );
                    user.setSid(rs.getInt("sid"));

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User getUserByUsername(String searchTerm) {
        String sql = "SELECT * FROM staff WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, searchTerm);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getString("name"),
                            rs.getInt("mobile"),
                            rs.getString("address"),
                            rs.getString("jobtitle"),
                            rs.getString("password")
                    );
                    user.setSid(rs.getInt("sid"));

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateUser(User user) {
        String sql = "UPDATE staff SET name = ?, mobile = ?, address = ?, jobtitle = ?, password = ? WHERE sid = ?"; // Updated table name to `staff`
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setInt(2, user.getMobile());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getJobtitle());
            pstmt.setString(5, user.getPassword());
            pstmt.setLong(6, user.getSid());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}