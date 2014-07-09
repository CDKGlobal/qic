package com.testing123.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AvailableResources {
	
	public static List<String> getAvailableDates() {
		List<String> dates = new ArrayList<String>();
		try {
			String home = System.getProperty("user.home");
			String absolutePath = home
				+ "/Perforce/chenc_sea-chenc-m_qic/Playpen/QIC2/Archives/";
			File folder = new File(absolutePath);
			String[] names = folder.list();
			for(String name : names) {
			    if (new File(absolutePath + name).isDirectory()) {
			    	String[] dateAndTime = name.split("T");
			        String[] date = dateAndTime[0].split("-");
			        String[] time = dateAndTime[1].split("-");
			        dates.add(name);
			    	System.out.println(name);
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return dates;
	}
}
