package com.testing123.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AvailableResources {
	
	public static int getAvailableDates() {
		try {
			String home = System.getProperty("user.home");
			String absolutePath = home
				+ "/Perforce/chenc_sea-chenc-m_qic/Playpen/QIC2/Archives/";
			File folder = new File(absolutePath);
			String[] names = folder.list();
			List<Date> dates = new ArrayList<Date>();
			for(String name : names) {
			    if (new File(absolutePath + name).isDirectory()) {
			    	
			        System.out.println(name);
			    }
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 3;
	}
}
