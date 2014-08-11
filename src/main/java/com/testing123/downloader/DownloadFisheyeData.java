package com.testing123.downloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.testing123.dataObjects.ChangedData;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.RevisionData;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.interfaces.FisheyeInterface;
import com.testing123.ui.Preferences;
import com.testing123.vaadin.UseSQLDatabase;

public class DownloadFisheyeData {

	private FisheyeInterface fisheye;
	private DatabaseInterface database;
	
	public DownloadFisheyeData(){
		this(new FisheyeQuery(), new UseSQLDatabase());
	}
	
	public DownloadFisheyeData(FisheyeInterface FI, DatabaseInterface DI){
		this.fisheye = FI;
		this.database = DI;
	}
	
	
	public List<ChangedData> getAllFisheyeUpdates() {
		return getAllFisheyeUpdates(getCurrentDate());
	}
	
	public List<ChangedData> getAllFisheyeUpdates(String date) {
		
		List<ConvertProject> listOfProjects = database.getAvailableProjects();
		
		List<ChangedData> returnedData = new ArrayList<ChangedData>();
		
		for (ConvertProject project : listOfProjects) {
			String repositoryName = getRepositoryName(project.getPath());
			String directoryName = getDirectoryName(project.getPath());
			if (repositoryExists(repositoryName)) {
				
				System.out.println("Project ID = " + project.getID());
				Map<String, Integer> mapForDatabase = database.getMapToID(project.getID());
				Set<String> setOfFilesInDatabase = new HashSet<String>(mapForDatabase.keySet());
				
				Set<RevisionData> revisionSet = fisheye.getRevisionsFromProject(repositoryName, directoryName, date);
				
				Set<RevisionData> aggregatedRevisionSet = aggregateRevisions(revisionSet);
				for (RevisionData revision : aggregatedRevisionSet) {
					ChangedData update = printUpdates(project, setOfFilesInDatabase, revision, mapForDatabase, date);
					if(update!=null){
						returnedData.add(update);
					}
				}
			}
		}
		System.out.println("Done");
		return returnedData;
	}
	
	
	
	private boolean repositoryExists(String repositoryName){
		Set<String> existingRepositories = new HashSet<String>(Arrays.asList(Preferences.FISHEYE_REPOS));
		return existingRepositories.contains(repositoryName);
	}
	

	private ChangedData printUpdates(ConvertProject project, Set<String> setOfFilesInDatabase, RevisionData r, Map<String, Integer> mapForDatabase, String date) {
		String formattedPath = formatFisheyePath(r.getFisheyePath());
		//System.out.println("fp = " + formattedPath);
		String path = r.getFisheyePath();
		//System.out.println("path = " + path);
		for(String sonarPath: setOfFilesInDatabase){
			//System.out.println("--" + sonarPath);
			if(path.endsWith(sonarPath) || formattedPath.endsWith(sonarPath)){
				//System.out.print(sonarPath +","+ getCurrentDate() +","+ r.getChurn() +","+ r.getAuthor().toString());
				return new ChangedData(mapForDatabase.get(sonarPath), date, r.getChurn(), r.getAuthor().toString());
			}
		}
			System.out.println(false + "=\t" + r.getFisheyePath());
			return null;
	}
	
	private String getCurrentDate(){
		DateTime today = new DateTime();
		return "" + today.getYear() + "-" + today.getMonthOfYear() + "-" + today.getDayOfMonth();
	}
	
	public Set<RevisionData> aggregateRevisions(Set<RevisionData> revisionSet){
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
	
	private String formatFisheyePath(String path) {
		path = path.substring(0, path.length() - 5);
		path = path.replaceAll("/", "\\.");
		return path;
	}

	private String getRepositoryName(String path) {
		String[] split = path.split("/");
		if (split.length > 1) {
			return split[1] + ".Perforce";
		}
		return "";
	}

	private String getDirectoryName(String path) {
		String[] split = path.split("/");
		if (split.length > 2) {
			int index = path.indexOf('/', 1);
			return path.substring(index + 1);
		}
		return "";
	}
}
