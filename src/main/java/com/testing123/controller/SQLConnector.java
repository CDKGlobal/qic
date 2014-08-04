package com.testing123.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.testing123.ui.Preferences;

public class SQLConnector {
	private Connection conn;
	
	/**
	 * Constructs a connection to the database with the default database set
	 */
	public SQLConnector() {
		this(Preferences.DB_NAME);
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
	
	/**
	 * Constructs a connection to the database
	 * 
	 * @param dbName database name
	 */
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
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query);
	}
	
	/**
	 * A query used to fetch data
	 * 
	 * @param query
	 * @return a ResultSet with all the data
	 */
	public ResultSet basicQuery(String query) {
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
    
	/**
	 * Executes a query 
	 * 
	 * @param query the string format of the query to be executed
	 * @return a ResultSet representing all the rows returned by the table
	 * @throws SQLException
	 */
	private ResultSet execute(String query) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		stmt = conn.createStatement();
		System.out.println(query);
		System.out.println();
		rs = stmt.executeQuery(query);
		return rs;
	}
}
