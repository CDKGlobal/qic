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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.FisheyeData;
import com.testing123.dataObjects.RevisionData;
import com.testing123.interfaces.FisheyeInterface;
import com.testing123.ui.Preferences;
import com.testing123.vaadin.RegexUtil;

public class FisheyeQuery implements FisheyeInterface {

	@Override
	public Set<RevisionData> getRevisionsFromProject(String repository, String directory, String date) {
		Set<RevisionData> revisionsFromFisheye = new HashSet<RevisionData>();
		String queryString = getProjectQueryAsString(repository, directory, getDateRange(date));
		URL queryURL = getQueryURL(queryString);
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
			inStream.close();
			buff.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return revisionsFromFisheye;
	}
	
	private static URL getQueryURL(String queryString) {
		String link = queryString.replaceAll("\\s+", "%20");
		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	private static String getProjectQueryAsString(String repository, String directory, String dateRange) {
		String linkHome = "http://fisheye.cobalt.com/search/";
		return linkHome + repository + "/?ql=" + " select revisions from dir \"" + directory + "\" where date in " + dateRange
				+ "and path like **.java and path like **/src/main/** return path,author,linesAdded,linesRemoved &csv=true";
	}
	
	private static String getDateRange(String date) {
		ConvertDate cDate = new ConvertDate(date);
		DateTime day = new DateTime(cDate.getYear(), cDate.getMonth(), cDate.getDay(), 7, 0);
		DateTime past = day.minusDays(1);
		return "[" + past.getYear() + "-" + past.getMonthOfYear() + "-" + past.getDayOfMonth() + "T07:00:00, " + day.getYear() + "-"
				+ day.getMonthOfYear() + "-" + day.getDayOfMonth() + "T07:00:00]";
	}
	
	private FisheyeData getJSONFromFisheye(URL url) {

		ObjectMapper mapper = new ObjectMapper();
		FisheyeData querriedData = new FisheyeData();

		try {
			querriedData = mapper.readValue(url, new TypeReference<FisheyeData>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return querriedData;
	}
	
	@Override
	public FisheyeData getRevisionList(String repository, String directory, ConvertPath path, String startDate, String endDate) {
		String dateRange = "[" + startDate + "," + endDate + "]";
		String queryString = getRevisionListQueryAsString(repository, directory, dateRange, path);
		System.out.println("url = " + queryString);
		URL queryURL = getQueryURL(queryString);
		FisheyeData changesets = getJSONFromFisheye(queryURL);
		return changesets;
	}
	
	private static String getRevisionListQueryAsString(String repository, String directory, String dateRange, ConvertPath path) {
		String linkHome = "http://fisheye.cobalt.com/rest-service-fe/search-v1/queryAsRows/";
		return linkHome + repository + ".json?query=" + " select revisions from dir \"" + directory + "\" where date in " + dateRange
				+ "and path like **/"+ path.getFisheyePath() + " return path,csid";
	}

}
