package com.testing123.downloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseConnectorTest {
    private static Connection conn;

    private static Statement stmt;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        conn = DriverManager.getConnection("jdbc:mysql://dc2pvpdc00059.vcac.dc2.dsghost.net:3306/TestDatabase?" +
                        "user=root&password=password");
        stmt = conn.createStatement();
        stmt.execute("DELETE FROM allFileHistory3");
        /*
         * rs).executeQuery(
         * "INSERT INTO allFileHistory3 (file_id, file_key, name, ncloc, complexity, dbdate,delta_complexity, churn, authors) "
         * + "VALUES (1111111, 'file_key', 'name', 5.0, 5.0, '2014-08-10', 5.0, 6, '[weiyoud, chena]');");
         */
    }


    @Test
    public void TestGetConnection() {
        Connection connection = DatabaseConnector.getConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM allFileHistory3 LIMIT 10");
            assertTrue(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * @Test
     * public void TestWriteToTxtFileAndUpsertMetrics() {
     * try {
     * Boolean b = stmt.execute(
     * "INSERT INTO allFileHistory3 (file_id, file_key, name, ncloc, complexity, dbdate,delta_complexity, churn, authors) "
     * + "VALUES (1111111, 'file_key', 'name', 5.0, 5.0, '2014-08-10', 5.0, 6, '[weiyoud, chena]');");
     * ResultSet rs =
     * stmt.executeQuery("SELECT * FROM allFileHistory3 where file_id = 1111111, file_key = 'file_key';");
     * assertTrue(rs.next());
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * }
     */
    @Test
    public void TestGetProjectPath() {
        assertEquals("/Advertising/platform/trunk/", DatabaseConnector.getProjectPath(17271));
    }


}
