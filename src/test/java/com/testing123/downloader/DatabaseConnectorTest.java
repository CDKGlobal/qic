package com.testing123.downloader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseConnectorTest {
    private static Connection conn;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        conn = DatabaseConnector.getConnection();

    }

    @Test
    public void TestGetConnection() {
        Connection conn = DatabaseConnector.getConnection();
        try {
            Statement stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * @Test
     * public void TestGetProjectPath() {
     * System.out.println("path = " + DatabaseConnector.getProjectPath(17271));
     * assertEquals("/Advertising/Platform/trunk/", DatabaseConnector.getProjectPath(17271));
     * }
     */


}
