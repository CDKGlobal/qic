package com.testing123.downloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            // \Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = getConnection();
            execute(conn);
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

    public static void execute(Connection conn) throws Exception {
        Statement stmt = null;
        boolean rs = true;

        try {
            stmt = conn.createStatement();
            rs = stmt.execute("CREATE TABLE d07_08(id INT NOT NULL, "
                            + "the_key VARCHAR(150) NOT NULL, name VARCHAR(70) NOT NULL, "
                            + "scope VARCHAR(5) NOT NULL, qualifier VARCHAR(5) NOT NULL, date VARCHAR(30) NOT NULL,"
                            + " loc DECIMAL(5,1), complexity DECIMAL(4,1));");
            rs = stmt.execute("LOAD DATA LOCAL INFILE '/Users/weiyoud/Perforce/"
                            + "weiyoud_sea-weiyoud_4033/playpen/QIC2/Archives/2014-07-08T06-07-31-0700.txt' INTO TABLE d07_08");
            // rs = stmt.executeQuery("SELECT * FROM d7");
            // System.out.println(rs);

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            /*
             * if (stmt.execute("SELECT foo FROM bar")) {
             * rs = stmt.getResultSet();
             * }
             */

            // Now do something with the ResultSet ....
        } finally {
            /*
             * it is a good idea to release
             * // resources in a finally{} block
             * // in reverse-order of their creation
             * // if they are no-longer needed
             * if (rs != null) {
             * try {
             * rs.close();
             * } catch (SQLException sqlEx) { // ignore }
             * rs = null;
             * }
             * if (stmt != null) {
             * try {
             * stmt.close();
             * } catch (SQLException sqlEx) { // ignore }
             * stmt = null;
             * }
             * }
             */
        }
    }
}