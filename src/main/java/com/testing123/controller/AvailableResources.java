package com.testing123.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import com.testing123.vaadin.ConvertDate;
import com.testing123.vaadin.WebData;

public class AvailableResources {
	
//	private static Calendar convertToDate(String sonarFormat) {
//		String[] dateAndTime = sonarFormat.split("T");
//        String[] date = dateAndTime[0].split("-");
//        String[] time = dateAndTime[1].split("-");
//		Calendar currentDate = new GregorianCalendar(
//				Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]),
//				Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
//		currentDate.setTimeZone(TimeZone.getTimeZone("GMT-" + time[3]));
//		return currentDate;
//	}
	
	public static List<WebData> getDataList(ConvertDate date){
		String stringDate = extractDate(date.getSonarFormat());
		return getDataList(stringDate);
	}
	
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
//			List<Calendar> availDates = new ArrayList<Calendar>();
//			for(String name : names) {
//			    if (new File(absolutePath + name).isDirectory()) {
//			    	Calendar convertedDate = convertToDate(name);
//			        availDates.add(convertedDate);
//			    	dates.add(name);
//			    }
//			}
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = SQLConnector.getConnection();
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
