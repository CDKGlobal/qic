package com.testing123.downloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            // \Class.forName("com.mysql.jdbc.Driver").newInstance();
            getConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {

            // Properties connectionProps = new Properties();

            Connection conn =
                            DriverManager.getConnection("jdbc:mysql://localhost:3306/dataList?" +
                                            "user=root&password=password");
            // Do something with the Connection

            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null; // ?

    }
}