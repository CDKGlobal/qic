package com.testing123.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.testing123.ui.Preferences;

public class SQLConnector {
	private Connection conn;
	
	/**
	 * Constructs a connection to the database with the default database set
	 */
	public SQLConnector() {
		this(Preferences.DB_NAME);
	}
	
	/**
	 * Constructs a connection to the database
	 * 
	 * @param dbName database name
	 */
	public SQLConnector(String dbName) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("Failed to initialize SQLConnector");
			this.conn = null;
		}
        this.conn = getConnection(dbName);
	}
	
    private Connection getConnection(String dbName) {
        try {
            Connection conn = 
            		DriverManager.getConnection("jdbc:" + Preferences.DB_SERVER + "/" + dbName 
            				+ "?user=" + Preferences.DB_USER + "&password=" + Preferences.DB_PASS);
            return conn;
        } catch (SQLException ex) {
        	ex.printStackTrace();
        	System.out.println("Could not make connection");
        }
        return null;
    }
	
	/**
	 * Returns the connection made to the database
	 * 
	 * @return
	 */
	public Connection getConn() {
		return conn;
	}
	
	/**
	 * A query used to update the database
	 * 
	 * @param query
	 * @throws SQLException
	 */
	public void updateQuery(String query) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.executeUpdate();
		stmt.close();
	}
	
	/**
	 * A query used to fetch data
	 * 
	 * @param query
	 * @return a ResultSet with all the data, null if it was a badly formatted query or it returned an empty ResultSet
	 */
	public ResultSet basicQuery(String query) {
		ResultSet results = null;
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			System.out.println(query);
			results = stmt.executeQuery();
			if (results.isBeforeFirst()) { 
				return results;
			}
		} catch (Exception e) {
			System.out.println("Badly Formatted Query: " + query);
			System.out.println();
		}
		return null;
	}
	
	/**
	 * Safely closes the connection to the database
	 */
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Close error");
		}
	}

}
