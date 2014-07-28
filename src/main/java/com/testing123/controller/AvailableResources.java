package com.testing123.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import com.testing123.vaadin.ConvertDate;
import com.testing123.vaadin.ConvertProject;
import com.testing123.vaadin.WebData;

public class AvailableResources {
	
	public static List<WebData> getDataList(ConvertDate date){
		String stringDate = extractDate(date.getSonarFormat());
		return getDataList(stringDate);
	}
	
	public static String extractDate(String sonarFormat) {
		return sonarFormat.replace("-", "_");
	}
	
	public static List<ConvertProject> getAvailableProjects() {
		List<ConvertProject> projects = new ArrayList<ConvertProject>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = SQLConnector.getConnection("dataList2");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name, the_key FROM projectList ORDER BY name ASC");
			while (rs.next()) {
				projects.add(new ConvertProject(rs.getString(1), rs.getString(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projects;
	}
	
	public static List<String> getAvailableAuthors() {
		List<String> authors = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = SQLConnector.getConnection("dataList2");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT user FROM authors ORDER BY name ASC");
			while (rs.next()) {
				authors.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authors;
	}
	
	public static List<String> getAvailableDates() {
		List<String> dates = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = SQLConnector.getConnection("dataList");
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
	
	public static List<WebData> getDataList(String date) {
		ResultSet results = null;
		try {
			results = SQLConnector.dataQuery("", "SELECT * FROM " + date + " WHERE QUALIFIER = 'CLA';");
			return SQLConnector.process(results, "ncloc", "complexity");
		} catch (Exception e) {
			return new ArrayList<WebData>();
		}
	}
}
