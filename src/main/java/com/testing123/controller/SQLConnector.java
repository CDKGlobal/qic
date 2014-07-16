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
	public static final String DATABASE_SERVER = "mysql://dc2pvpdc00059.vcac.dc2.dsghost.net:3306";
	//public static final String 
	
	public static ResultSet basicQuery(String query) {
        try {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = getConnection();
            return execute(conn, query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
	}
	
	public static ResultSet dataQuery(String date, String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection conn = getConnection();
            return execute(conn, query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
	}
	
    public static Connection getConnection() {
        try {
        	String[] home = System.getProperty("user.home").split("/");
        	String user = home[2];
            Connection conn = DriverManager.getConnection("jdbc:" + SQLConnector.DATABASE_SERVER + "/dataList?user=" + user + "&password=password");
            return conn;
        } catch (SQLException ex) {
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
		return rs;
	}

	public static List<WebData> process(ResultSet rs, String... msrKeys) {
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
				
				String[] msrKey = msrKeys;
				
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
