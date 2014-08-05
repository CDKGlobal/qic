package com.testing123.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import com.testing123.vaadin.ConvertDate;
import com.testing123.vaadin.ConvertProject;

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
		SQLConnector connector = new SQLConnector();
		ResultSet results = connector.basicQuery("SELECT name, project_key, project_id, path FROM projectList ORDER BY name ASC");
		try {
			while (results.next()) {
				String name = results.getString("name");
				String projectKey = results.getString("project_key");
				int projectID = results.getInt("project_id");
				String path = results.getString("path");
				if (path != null && path.length() > 1) {
					projects.add(new ConvertProject(name, projectKey, projectID, path));
				}
			}
			connector.close();
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
			SQLConnector connector = new SQLConnector();
			ResultSet rs = connector.basicQuery("SELECT user FROM authors ORDER BY user ASC");
			while (rs.next()) {
				authors.add(rs.getString(1));
			}
			connector.close();
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
			SQLConnector conn = new SQLConnector();
			ResultSet rs = conn.basicQuery("SELECT * FROM dates");
			while (rs.next()) {
				dates.add(new ConvertDate(rs.getString("display")));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return dates;
	}
}
