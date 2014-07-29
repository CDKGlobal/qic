package com.testing123.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.testing123.ui.Preferences;
import com.testing123.vaadin.Msr;
import com.testing123.vaadin.WebData;

public class SQLConnector {
	
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
            conn = getConnection(Preferences.DB_NAME);
            return execute(conn, query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
	}
	
    public static Connection getConnection(String dbName) {
        try {
            Connection conn = 
            		DriverManager.getConnection("jdbc:" + Preferences.DB_SERVER + "/" + dbName 
            				+ "?user=" + Preferences.DB_USER + "&password=" + Preferences.DB_PASS);
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
		System.out.println(query);
		System.out.println();
		rs = stmt.executeQuery(query);
		return rs;
	}
	
	public static void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Close error");
		}
	}
	
	public static String getUser() {
    	String home = System.getProperty("user.home").replace("/Users/", "");
    	return home;
	}
}
