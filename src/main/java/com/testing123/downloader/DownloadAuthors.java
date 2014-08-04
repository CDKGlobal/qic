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

	public static void main(String[] args) {
		addDateStamp();
		addAuthors();
	}
	
	public static void addDateStamp() {
		SQLConnector connector = new SQLConnector("dataList4");
		DateTime today = new DateTime();
		String query = null;
		if (today.getMonthOfYear() < 10) {
			query = "INSERT INTO dates (display) VALUES ('" + today.getYear() + "-" + 
				frmtDigit(today.getMonthOfYear()) + "-" + frmtDigit(today.getDayOfMonth()) + "')";
		} else {
			query = "INSERT INTO dates (display) VALUES ('" + today.getYear() + "-" + 
				frmtDigit(today.getMonthOfYear()) + "-" + frmtDigit(today.getDayOfMonth()) + "')";
		}
		System.out.println(query);
		try {
			connector.updateQuery(query);
		} catch (SQLException e) {
			System.out.println("today's date already exists");
		}
		connector.close();
	}
	
	private static void addAuthors() {
		SQLConnector connector = new SQLConnector("dataList4");
		Set<String> all = new HashSet<String>();
		int k = 0;
		for (String repository : Preferences.FISHEYE_REPOS) {
			String home = "http://fisheye.cobalt.com/search/";
			String link = home + repository + "/?ql=" + getQuery();
			link = link.replaceAll("\\s+", "%20");
			try {
				URL url  = new URL(link);
				System.out.println(link);
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
				System.out.println(i + " rows added");
				k += i;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(k + " rows added total!");
		connector.close();
	}

	private static int upload(SQLConnector connector, String vals) {
		try {
			String query = "INSERT INTO authors (user) VALUES (" + vals + ")";
			System.out.println(query);
			connector.updateQuery(query);
			return 1;
		} catch (Exception e) {
			System.out.println(vals + " already exists");
		}
		return 0;
	}

	private static String frmtDigit(int a) {
		if (a < 10) {
			return "0" + a;
		} else {
			return a + "";
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
