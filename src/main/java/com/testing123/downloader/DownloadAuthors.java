package com.testing123.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.testing123.controller.SQLConnector;

public class DownloadAuthors {
	private static String[] repositories = new String[] {
			"Advertising.Perforce", "Core.Perforce", "Intelligence.Perforce",
			"OpenPlatform.Perforce", "Owner.Perforce",
			"ProfessionalServices.Perforce", "ReleaseEngineering.Perforce",
			"Social.Perforce" };

	public static void main(String[] args) {
		addAuthors();
	}

	private static void addAuthors() {
		Set<String> all = new HashSet<String>();
		int k = 0;
		for (String repository : repositories) {
			String home = "http://fisheye.cobalt.com/search/";
			String link = home + repository + "/?ql=" + getQuery();
			link = link.replaceAll("\\s+", "%20");
			Connection conn = SQLConnector.getConnection("dataList2");
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
		            System.out.println(content);
		            content = buff.readLine();
		        }
		        int i = 0;
				for (String auth : all) {
					i += upload(conn, auth);
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
	}

	private static int upload(Connection conn, String vals) {
		try {
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO authors (user) VALUES (" + vals + ")";
			System.out.println(query);
			stmt.executeUpdate(query);
			return 1;
		} catch (Exception e) {
			System.out.println(vals + " already exists");
		}
		return 0;
	}

	private static String getQuery() {
		DateTime today = new DateTime();
        DateTime past = new DateTime().minusDays(3);
		return "select revisions where date in [" + past.getYear() + "-" + past.getMonthOfYear() + "-" + 
        		past.getDayOfMonth() + "T07:00:00, " + today.getYear() + "-" + today.getMonthOfYear() + "-" 
        		+ today.getDayOfMonth() + "T07:00:00] return author&csv=true";
	}
}
