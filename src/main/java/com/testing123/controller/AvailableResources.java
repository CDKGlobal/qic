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
	
//	public static List<WebData> getDataList(ConvertDate date){
//		String stringDate = extractDate(date.getSonarFormat());
//		return getDataList(stringDate);
//	}
	
//	private static String extractDate(String sonarFormat) {
//		return sonarFormat.replace("-", "_");
//	}
	
	/**
	 * Fetches the available list of projects from the database
	 * 
	 * @return a list of all the available projects that are stored in the database
	 */
	public static List<ConvertProject> getAvailableProjects() {
		List<ConvertProject> projects = new ArrayList<ConvertProject>();
		try {
			SQLConnector connector = new SQLConnector();
			ResultSet rs = connector.basicQuery("SELECT name, project_key, project_id FROM projectList ORDER BY name ASC");
			while (rs.next()) {
				projects.add(new ConvertProject(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return projects;
	}
	
	/**
	 * Fetches the available list of authors from the database
	 * 
	 * @return a list of all the available authors that have made edits
	 */
	public static List<String> getAvailableAuthors() {
		List<String> authors = new ArrayList<String>();
		try {
			SQLConnector connector = new SQLConnector("dataList2");
			ResultSet rs = connector.basicQuery("SELECT user FROM authors ORDER BY user ASC");
			while (rs.next()) {
				authors.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authors;
	}
	
	/**
	 * Fetches the available list of dates from the database
	 * 
	 * @return a list of all the available dates that have been stored in the database
	 */
	public static List<ConvertDate> getAvailableDates() {
		List<ConvertDate> dates = new ArrayList<ConvertDate>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = new SQLConnector("dataList").getConn();
			DatabaseMetaData md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
				String date = rs.getString(3);
				Statement stmt = conn.createStatement();
				ResultSet rs1 = stmt.executeQuery("SELECT * FROM " + date + " LIMIT 1;");
				if (rs1.next()) {
					dates.add(new ConvertDate(rs1.getString("date")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return dates;
	}
}
