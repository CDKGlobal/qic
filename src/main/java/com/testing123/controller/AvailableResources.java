package com.testing123.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.testing123.vaadin.Msr;
import com.testing123.vaadin.WebData;

// ooooo
public class AvailableResources {

    public static String extractDate(String sonarFormat) {
        String[] dateAndTime = sonarFormat.split("T");
        String[] date = dateAndTime[0].split("-");
        return "d" + date[1] + "_" + date[2];
    }

    public static List<String> getAvailableDates() {
        List<String> dates = new ArrayList<String>();

        try {
            //			String home = System.getProperty("user.home");
            //			String absolutePath = home
            //				+ "/Perforce/chenc_sea-chenc-m_qic/Playpen/QIC2/Archives/";
            //			File folder = new File(absolutePath);
            //			String[] names = folder.list();
            List<Calendar> availDates = new ArrayList<Calendar>();
            //			for(String name : names) {
            //			    if (new File(absolutePath + name).isDirectory()) {
            //			    	Calendar convertedDate = convertToDate(name);
            //			        availDates.add(convertedDate);
            //			    	dates.add(name);
            //			    }
            //			}
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = getConnection();
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                String date = rs.getString(3);
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery("SELECT * FROM " + date + " LIMIT 1;");
                if (rs1.next()) {
                    dates.add(rs1.getString("date"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dates;
    }

    public static void main(String[] args) {
        queryData("SELECT * FROM d07_07");
    }

    public static List<WebData> getDataList(String date) {
        ResultSet results = queryData("SELECT * FROM " + date + " WHERE QUALIFIER = 'CLA';");
        return process(results);
    }

    public static ResultSet queryData(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = getConnection();
            return execute(conn, query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ResultSet queryData(String date, String query) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = getConnection();
            return execute(conn, query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static Connection getConnection() {
        try {
            String[] home = System.getProperty("user.home").split("/");
            String user = home[2];
            Connection conn = DriverManager.getConnection("jdbc:mysql://dc2pvpdc00059.vcac.dc2.dsghost.net:3306/dataList?user=" + user + "&password=password");
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }

    public static ResultSet execute(Connection conn, String query) throws Exception {
        Statement stmt = null;
        ResultSet rs = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);
        System.out.println(query);
        return rs;
    }

    private static List<WebData> process(ResultSet rs) {
        List<WebData> processed = new ArrayList<WebData>();
        try {
            while (rs.next()) {
                WebData data = new WebData();
                data.setId(rs.getInt("id"));
                data.setKey(rs.getString("the_key"));
                data.setName(rs.getString("name"));
                data.setScope(rs.getString("scope"));
                data.setQualifier(rs.getString("qualifier"));
                data.setDate(rs.getString("date"));

                List<Msr> msr = new ArrayList<Msr>();

                Msr ncloc = new Msr();
                ncloc.setKey("ncloc");
                ncloc.setVal(rs.getDouble("loc"));

                Msr complexity = new Msr();
                complexity.setKey("complexity");
                complexity.setVal(rs.getDouble("complexity"));

                msr.add(ncloc);
                msr.add(complexity);
                data.setMsr(msr);
                processed.add(data);
            }
            return processed;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}