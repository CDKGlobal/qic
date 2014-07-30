package com.testing123.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.testing123.ui.Preferences;

public class SQLConnector {
	private Connection conn;
	
	public SQLConnector() {
		this(Preferences.DB_NAME);
	}
	
	public SQLConnector(String dbName) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        this.conn = getConnection(dbName);
	}
	
	public Connection getConn() {
		return conn;
	}
	
	public void updateQuery(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query);
	}
	
	public ResultSet basicQuery(String query) {
		return querySQL(query);
	}
	
	public ResultSet querySQL(String query) {
		ResultSet results = null;
		try {
			results = execute(query);
			if (results.isBeforeFirst()) { 
				return results;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    private Connection getConnection(String dbName) {
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
    
	public ResultSet execute(String query) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		stmt = conn.createStatement();
		System.out.println(query);
		System.out.println();
		rs = stmt.executeQuery(query);
		return rs;
	}
	
	public void close() {
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
