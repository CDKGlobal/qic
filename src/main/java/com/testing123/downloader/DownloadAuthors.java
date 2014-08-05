package com.testing123.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.testing123.controller.SQLConnector;
import com.testing123.ui.Preferences;

public class DownloadAuthors {
	private SQLConnector connector;

	public DownloadAuthors() {
		connector = new SQLConnector();
	}
	
	public void addDateStamp() {
		DateTime today = new DateTime();
		String query = null;
		query = "INSERT INTO dates (display, datescol) VALUES ('" + DataSupportMain.getFrmtDate(today) + "', '" 
			 + DataSupportMain.getFrmtDate(today) + "')";
		System.out.println(query);
		try {
			connector.updateQuery(query);
		} catch (SQLException e) {
			System.out.println("today's date already exists");
		}
		connector.close();
	}
	
	public void addAuthors() {
		Set<String> all = new HashSet<String>();
		for (String repository : Preferences.FISHEYE_REPOS) {
			String home = "http://fisheye.cobalt.com/search/";
			String link = home + repository + "/?ql=" + getQuery();
			link = link.replaceAll("\\s+", "%20");
			try {
				URL url  = new URL(link);
		        URLConnection urlConn = url.openConnection();
		        InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		        BufferedReader buff= new BufferedReader(inStream);
		        String junk = buff.readLine();
		        String content = buff.readLine();		        
		        while (content != null){
		        	all.add(content);
		            content = buff.readLine();
		        }
		        int i = 0;
				for (String auth : all) {
					i += upload(connector, auth);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		connector.close();
	}

	private static int upload(SQLConnector connector, String vals) {
		try {
			String query = "INSERT INTO authors (user) VALUES (" + vals + ")";
			connector.updateQuery(query);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	private static String getQuery() {
		DateTime today = new DateTime();
        DateTime past = new DateTime().minusDays(3);
		return "select revisions where date in [" + past.getYear() + "-" + past.getMonthOfYear() + "-" + 
        		past.getDayOfMonth() + "T07:00:00, " + today.getYear() + "-" + today.getMonthOfYear() + "-" 
        		+ today.getDayOfMonth() + "T07:00:00] return author&csv=true";
	}
}
