package com.testing123.downloader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.testing123.vaadin.WebData;

public class DatabaseConnector {
    /*   public void connectAndExecute(String date) {
        try {

            // The newInstance() call is a work around for some
            // broken Java implementations
            // \Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = getConnection();
            execute(conn, date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

    public Connection getConnection() {
        try {
            // Properties connectionProps = new Properties();
            Connection conn =
                            DriverManager.getConnection("jdbc:mysql://localhost/dataList2?" +
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
    }

    public void createDbAndLoadTableForPrject(Connection conn, List<WebData> projectList) {
        Statement stmt = null;
        boolean rs = true;
        System.out.println("Ok before conn.createStatement");
        try {
            stmt = conn.createStatement();
            for (WebData project : projectList) {
                System.out.println(project.getId() + "_id");
                rs = stmt.execute("CREATE TABLE id_" + project.getId() + " (id INT NOT NULL, "
                                + "the_key VARCHAR(160) NOT NULL, name VARCHAR(80) NOT NULL, "
                                + "scope VARCHAR(5) NOT NULL, qualifier VARCHAR(5) NOT NULL," +
                                "date VARCHAR(30) NOT NULL,"
                                + " ncloc DECIMAL(8,1), complexity DECIMAL(8,1));");
                rs = stmt.execute("LOAD DATA LOCAL INFILE '//Users/weiyoud/Perforce/weiyoud_sea-weiyoud_4033/Playpen/QIC2/Archives/"
                                + "projectList/" + project.getId() + ".txt' INTO TABLE id_" + project.getId() + ";");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDbAndLoadTable(Connection conn, String date, String projectName) {
        Statement stmt = null;
        boolean rs = true;
        try {
            stmt = conn.createStatement();
            //       for (WebData project : projectList) {

            rs = stmt.execute("CREATE TABLE " + date.replace("-", "_") + "(id INT NOT NULL, "
                            + "the_key VARCHAR(160) NOT NULL, name VARCHAR(80) NOT NULL, "
                            + "scope VARCHAR(5) NOT NULL, qualifier VARCHAR(5) NOT NULL,"
                            + " date VARCHAR(30) NOT NULL,"
                            + " ncloc DECIMAL(5,1), complexity DECIMAL(4,1));");

            rs = stmt.execute("LOAD DATA LOCAL INFILE '//Users/weiyoud/Perforce/weiyoud_sea-weiyoud_4033/Playpen/QIC2/Archives/"
                            + date + "/17271/files.txt' INTO TABLE " + projectName + ";");// date.replace("-",
            // "_") + ";");
            rs = stmt.execute("LOAD DATA LOCAL INFILE '//Users/weiyoud/Perforce/weiyoud_sea-weiyoud_4033/Playpen/QIC2/Archives/"
                            + date + "/17271/filesHistory.txt' INTO TABLE " + projectName + "History" + ";");


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
        } finally {
            /*
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