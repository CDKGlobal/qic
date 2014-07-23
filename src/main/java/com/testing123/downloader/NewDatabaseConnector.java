package com.testing123.downloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class NewDatabaseConnector {
    private Connection conn;

    public NewDatabaseConnector() {
        try {
            // Properties connectionProps = new Properties();
            Connection conn =
                            DriverManager.getConnection("jdbc:mysql://localhost/dataList?" +
                                            "user=root&password=password");
            // Do something with the Connection
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }
    /*
    public Connection getConnection() {
        try {
            // Properties connectionProps = new Properties();
            Connection conn =
                            DriverManager.getConnection("jdbc:mysql://localhost/dataList?" +
                                            "user=root&password=password");
            // Do something with the Connection
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }*/

    public void createTable(String date) {
        Statement stmt = null;
        boolean rs = true;
        try {
            stmt = conn.createStatement();
            /*
             * rs = stmt.execute("CREATE TABLE " + date.replace("-", "_") + "(id INT NOT NULL, "
             * + "the_key VARCHAR(160) NOT NULL, name VARCHAR(80) NOT NULL, "
             * + "scope VARCHAR(5) NOT NULL, qualifier VARCHAR(5) NOT NULL,"
             * + " date VARCHAR(30) NOT NULL,"
             * + " ncloc DECIMAL(5,1), complexity DECIMAL(4,1));");
             * if (idList.contains()) {
             * rs = stmt.execute();
             * }
             * rs = stmt.execute();
             * // loadTable(date, stmt);
             */
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            // or alternatively, if you don't know ahead of time that
            // the query will be a SELECT...

            /*
             * if (stmt.execute("SELECT foo FROM bar")) {
             * rs = stmt.getResultSet();
             * }
             */

            // Now do something with the ResultSet ....
        }
    }

    public void loadTable(String date) {
        boolean rs = true;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            rs = stmt.execute("LOAD DATA LOCAL INFILE '//Users/weiyoud/Perforce/weiyoud_sea-weiyoud_4033/Playpen/QIC2/Archives/" + date + "/17271/"
                            + "files.txt' INTO TABLE " + date.replace("-", "_") + ";");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}