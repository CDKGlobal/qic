package com.testing123.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class AvailableResources {
	
	public static Calendar convertToDate(String sonarFormat) {
		String[] dateAndTime = sonarFormat.split("T");
        String[] date = dateAndTime[0].split("-");
        String[] time = dateAndTime[1].split("-");
		Calendar currentDate = new GregorianCalendar(
				Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]),
				Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
		currentDate.setTimeZone(TimeZone.getTimeZone("GMT-" + time[3]));
		return currentDate;
	}
	
	public static List<String> getAvailableDates() {
		List<String> dates = new ArrayList<String>();
		try {
			String home = System.getProperty("user.home");
			String absolutePath = home
				+ "/Perforce/chenc_sea-chenc-m_qic/Playpen/QIC2/Archives/";
			File folder = new File(absolutePath);
			String[] names = folder.list();
			List<Calendar> availDates = new ArrayList<Calendar>();
			for(String name : names) {
			    if (new File(absolutePath + name).isDirectory()) {
			    	Calendar convertedDate = convertToDate(name);
			        availDates.add(convertedDate);
			    	dates.add(convertedDate.getTime().toString());
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return dates;
	}
}
