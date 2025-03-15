package com.megacitycab.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public void initializeDefaultUsers() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();

            // Disable auto-commit to start a transaction
            conn.setAutoCommit(false);

            // Generate hashed passwords and salts
            String adminSalt = PasswordUtil.generateSalt();
            String adminHashedPassword = PasswordUtil.hashPassword("admin123", adminSalt);

            String managerSalt = PasswordUtil.generateSalt();
            String managerHashedPassword = PasswordUtil.hashPassword("manager123", managerSalt);

            String receptionistSalt = PasswordUtil.generateSalt();
            String receptionistHashedPassword = PasswordUtil.hashPassword("recep123", receptionistSalt);

            // SQL INSERT statements
            String insertAdminSQL = "INSERT INTO employee (username, password, password_salt, full_name, role, email, phone, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(insertAdminSQL);

            // Add admin user
            pstmt.setString(1, "admin");
            pstmt.setString(2, adminHashedPassword);
            pstmt.setString(3, adminSalt);
            pstmt.setString(4, "Admin User");
            pstmt.setString(5, "ADMIN");
            pstmt.setString(6, "admin@megacitycab.com");
            pstmt.setString(7, "1234567890");
            pstmt.setString(8, "ACTIVE");
            pstmt.executeUpdate();

            pstmt.close(); // Close previous statement before creating a new one

            String insertManagerSQL = "INSERT INTO employee (username, password, password_salt, full_name, role, email, phone, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(insertManagerSQL);

            // Add manager user
            pstmt.setString(1, "manager1");
            pstmt.setString(2, managerHashedPassword);
            pstmt.setString(3, managerSalt);
            pstmt.setString(4, "Manager User");
            pstmt.setString(5, "MANAGER");
            pstmt.setString(6, "manager1@megacitycab.com");
            pstmt.setString(7, "2345678901");
            pstmt.setString(8, "ACTIVE");
            pstmt.executeUpdate();

            pstmt.close(); // Close previous statement before creating a new one

            String insertReceptionistSQL = "INSERT INTO employee (username, password, password_salt, full_name, role, email, phone, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(insertReceptionistSQL);

            // Add receptionist user
            pstmt.setString(1, "receptionist1");
            pstmt.setString(2, receptionistHashedPassword);
            pstmt.setString(3, receptionistSalt);
            pstmt.setString(4, "Receptionist User");
            pstmt.setString(5, "RECEPTIONIST");
            pstmt.setString(6, "receptionist1@megacitycab.com");
            pstmt.setString(7, "3456789012");
            pstmt.setString(8, "ACTIVE");
            pstmt.executeUpdate();

            // Commit the transaction if all inserts were successful
            conn.commit();

        } catch (SQLException e) {
            // Rollback the transaction if any insert fails
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();

        } finally {
            // Restore auto-commit and close resources in the finally block
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Restore auto-commit
                    DBConnection.closeConnection(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
