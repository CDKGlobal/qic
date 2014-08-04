package com.testing123.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.testing123.controller.SQLConnector;
import com.testing123.dataObjects.ProjectListData;
import com.testing123.dataObjects.RepositoryAndDirectoryData;
import com.testing123.dataObjects.RevisionData;
import com.testing123.vaadin.RegexUtil;

public class DownloadFisheyeData {

	public static void main(String[] args) {

		Set<ProjectListData> setOfProjects = getProjectsSet();
		for (ProjectListData project : setOfProjects) {
			
			RepositoryAndDirectoryData projectPath = getRepositoryAndDirectoryNameFromPath(project.getPath());
			if(!"Advertising.Perforce".equals(projectPath.getRepository())){
				continue;
			}
			
			System.out.println("Project ID = " + project.getProjectID());
			
			Map<String, Integer> mapForDatabase = getMapToID(project.getProjectID());
			
			Set<String> setOfFilesInDatabase = new HashSet<String>();
			setOfFilesInDatabase.addAll(mapForDatabase.keySet());
			
			Set<RevisionData> revisionSet = getRevisionsFromProject(projectPath.getRepository(), projectPath.getDirectory());
			Set<RevisionData> aggregatedRevisionsSet = aggregateRevisions(revisionSet);
			
			for (RevisionData r : revisionSet) {
				String path = formatFisheyePath(r.getFisheyePath());
				boolean b = false;
				for(String sonarPath: setOfFilesInDatabase){
					if(path.contains(sonarPath)){
						if(!b){
							b=true;
						}else{
							System.out.println("There are two that fit = " + sonarPath);
						}
					}
				}
				if(!b){System.out.println(b + "=\t" + path);}
			}
		}

		System.out.println("Done");

	}
	
	public static Set<RevisionData> aggregateRevisions(Set<RevisionData> revisionSet){
		Map<String, RevisionData> revisionMap = new HashMap<String, RevisionData>();
		Set<RevisionData> aggregateSet = new HashSet<RevisionData>();
		for(RevisionData revision: revisionSet){
			String fisheyePath = revision.getFisheyePath();
			if(revisionMap.containsKey(fisheyePath)){
				revisionMap.get(fisheyePath).combine(revision);
			}else{
				revisionMap.put(fisheyePath, revision);
			}
		}
		for(String fisheyePath: revisionMap.keySet()){
			aggregateSet.add(revisionMap.get(fisheyePath));
		}
		return aggregateSet;
	}
	
	private static String formatFisheyePath(String path) {
		System.out.println(path);
		path = path.substring(0, path.length() - 5);
		path = path.replaceAll("/", "\\.");
		System.out.println(path);
		return path;
	}

	public static Map<String, Integer> getMapToID(int projectID) {
		SQLConnector connector = new SQLConnector();
		ResultSet results = connector.basicQuery("SELECT file_id,file_key FROM allFileList WHERE project_id=" + projectID + ";");
		Map<String, Integer> mapNameToID = new HashMap<String, Integer>();
		if (results != null) {
			try {
				while (results.next()) {
					int fileID = results.getInt("file_id");
					String fileKey = results.getString("file_key");
					String formattedName = nameFormat(fileKey);
					if (mapNameToID.containsKey(formattedName)) {
						System.out.println("two files names of " + formattedName + " exist");
					} else {
						
						mapNameToID.put(formattedName, fileID);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		connector.close();
		return mapNameToID;
	}

	private static String nameFormat(String fileKey) {
		String[] split = fileKey.split(":");
		int length = split.length;
		String formattedName = split[length - 1];
		return formattedName;
	}

	private static RepositoryAndDirectoryData getRepositoryAndDirectoryNameFromPath(String path) {
		String repository = getRepositoryName(path);
		String directory = getDirectoryName(path);
		return new RepositoryAndDirectoryData(repository, directory);
	}

	private static Set<ProjectListData> getProjectsSet() {
		SQLConnector connector = new SQLConnector();
		ResultSet results = connector.basicQuery("SELECT project_id, project_key, path FROM projectList;");
		Set<ProjectListData> projectDataSet = new HashSet<ProjectListData>();
		try {
			while (results.next()) {
				int projectID = results.getInt("project_id");
				String projectKey = results.getString("project_key");
				String path = results.getString("path");
				if (path.length() != 0)
					projectDataSet.add(new ProjectListData(projectID, projectKey, path));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connector.close();
		return projectDataSet;
	}

	private static Set<RevisionData> getRevisionsFromProject(String repository, String directory) {
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

	private static String getRepositoryName(String path) {
		String[] split = path.split("/");
		if (split.length > 1) {
			return split[1] + ".Perforce";
		}
		return "";
	}

	private static String getDirectoryName(String path) {
		String[] split = path.split("/");
		if (split.length > 2) {
			int index = path.indexOf('/', 1);
			return path.substring(index + 1);
		}
		return "";
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
		dateRange = "[2014-07-30,2014-08-03]";
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
