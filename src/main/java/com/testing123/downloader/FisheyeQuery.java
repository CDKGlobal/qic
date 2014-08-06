package com.testing123.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.testing123.dataObjects.RevisionData;
import com.testing123.vaadin.RegexUtil;

public class FisheyeQuery implements FisheyeInterface {

	@Override
	public Set<RevisionData> getRevisionsFromProject(String repository, String directory) {
		Set<RevisionData> revisionsFromFisheye = new HashSet<RevisionData>();
		URL queryURL = getQueryURL(repository, directory);
		try {
			URLConnection urlConn = queryURL.openConnection();
			InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
			BufferedReader buff = new BufferedReader(inStream);
			buff.readLine();
			String revision = buff.readLine();
			while (revision != null) {
				if(RegexUtil.isRevisionData(revision)){
					revisionsFromFisheye.add(new RevisionData(revision));
				}else{
					System.out.println("could not parse:	" + revision);
				}
				revision = buff.readLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return revisionsFromFisheye;
	}
	
	private static URL getQueryURL(String repository, String directory) {
		String queryString = getQueryAsString(repository, directory);
		String link = queryString.replaceAll("\\s+", "%20");
		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private static String getQueryAsString(String repository, String directory) {
		String linkHome = "http://fisheye.cobalt.com/search/";
		String dateRange = getDateRange();
		dateRange = "[2014-07-01,2014-08-03]";
		return linkHome + repository + "/?ql=" + " select revisions from dir \"" + directory + "\" where date in " + dateRange
				+ "and path like **.java and path like **/src/main/** return path,author,linesAdded,linesRemoved &csv=true";
	}
	
	private static String getDateRange() {
		DateTime today = new DateTime();
		DateTime past = new DateTime().minusDays(1);
		return "[" + past.getYear() + "-" + past.getMonthOfYear() + "-" + past.getDayOfMonth() + "T07:00:00, " + today.getYear() + "-"
				+ today.getMonthOfYear() + "-" + today.getDayOfMonth() + "T07:00:00]";
	}

}
