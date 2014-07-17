package com.testing123.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.testing123.vaadin.Msr;
import com.testing123.vaadin.WebData;

public class SQLConnector {
	public static final String DB_SERVER = "mysql://dc2pvpdc00059.vcac.dc2.dsghost.net:3306";
	public static final String DB_USER = getUser();
	public static final String DB_PASS = "password";
	public static final String DB_NAME = "dataList";
	
	public static ResultSet basicQuery(String query) {
		return querySQL(query);
	}
	
	public static ResultSet dataQuery(String date, String query) {
		return querySQL(query);
	}
	
	public static ResultSet querySQL(String query) {
        Connection conn = null;
		try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = getConnection();
            return execute(conn, query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}
	
    public static Connection getConnection() {
        try {
            Connection conn = 
            		DriverManager.getConnection("jdbc:" + SQLConnector.DB_SERVER + "/" + DB_NAME + "?user=" + DB_USER + "&password=" + DB_PASS);
            return conn;
        } catch (SQLException ex) {
        	System.out.println("Could not make Connection:");
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
        }
        return null;
    }
    
	public static ResultSet execute(Connection conn, String query) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		return rs;
	}

	public static List<WebData> process(ResultSet rs, String... metrics) {
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
				
				List<Msr> msrList = new ArrayList<Msr>();

				String[] msrKeys = metrics;
				for (String msrKey : msrKeys) {
					Msr msr = new Msr();
					msr.setKey(msrKey);
					msr.setVal(rs.getDouble(msrKey));
					msrList.add(msr);
				}
				data.setMsr(msrList);
				processed.add(data);
			}
			return processed;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getUser() {
    	String[] home = System.getProperty("user.home").split("/");
    	return home[2];
	}
}
