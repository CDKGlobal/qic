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
import com.testing123.dataObjects.ConvertPath;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.RepoAndDirData;
import com.testing123.dataObjects.RevisionData;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.interfaces.FisheyeInterface;
import com.testing123.ui.Preferences;
import com.testing123.vaadin.UseSQLDatabase;

public class DownloadFisheyeData {

	private FisheyeInterface fisheye;
	private DatabaseInterface database;
	private final Set<String> FISHEYE_REPOS;

	public DownloadFisheyeData() {
		this(new FisheyeQuery(), new UseSQLDatabase());
	}

	public DownloadFisheyeData(FisheyeInterface FI, DatabaseInterface DI) {
		this.fisheye = FI;
		this.database = DI;
		FISHEYE_REPOS = new HashSet<String>(Arrays.asList(Preferences.FISHEYE_REPOS));
	}

	public List<ChangedData> getAllFisheyeUpdates() {
		return getAllFisheyeUpdates(getCurrentDate());
	}

	public List<ChangedData> getAllFisheyeUpdates(String date) {
		List<ConvertProject> listOfProjects = database.getAvailableProjects();
		List<ChangedData> returnedData = new ArrayList<ChangedData>();
		for (ConvertProject project : listOfProjects)
			addChangedDataToSet(date, returnedData, project);
		System.out.println("Done");
		return returnedData;
	}

	private void addChangedDataToSet(String date, List<ChangedData> returnedData, ConvertProject project) {
		RepoAndDirData path = new RepoAndDirData(project.getPath());
		if (repositoryExists(path.getRepositoryName())) {
			System.out.println("Project ID = " + project.getID());
			Set<RevisionData> revisionSet = fisheye.getRevisionsFromProject(path.getRepositoryName(), path.getDirectoryName(), date);
			Set<RevisionData> aggregatedRevisionSet = aggregateRevisions(revisionSet);
			Map<String, Integer> mapForDatabase = database.getMapToID(project.getID());
			Set<String> setOfFilesInDatabase = new HashSet<String>(mapForDatabase.keySet());
			for (RevisionData revision : aggregatedRevisionSet) {
				String sonarPath = pathInDatabase(revision.getFisheyePath(), setOfFilesInDatabase);
				if (sonarPath != null) {
					returnedData.add(new ChangedData(mapForDatabase.get(sonarPath), date, revision.getChurn(), revision.getAuthor()
							.toString()));
				} else {
					System.out.println("Found no match in Database for:" + "=\t" + revision.getFisheyePath());
				}
			}
		}
	}

	private String pathInDatabase(String fisheyePath, Set<String> setOfFilesInDatabase) {
		ConvertPath revisionKey = new ConvertPath(fisheyePath);
		for (String sonarPath : setOfFilesInDatabase) {
			if (revisionKey.equals(sonarPath)) {
				return sonarPath;
			}
		}
		return null;
	}

	public Set<RevisionData> aggregateRevisions(Set<RevisionData> revisionSet) {
		Map<String, RevisionData> revisionMap = new HashMap<String, RevisionData>();
		Set<RevisionData> aggregateSet = new HashSet<RevisionData>();
		for (RevisionData revision : revisionSet) {
			String fisheyePath = revision.getFisheyePath();
			if (revisionMap.containsKey(fisheyePath)) {
				revisionMap.get(fisheyePath).combine(revision);
			} else {
				revisionMap.put(fisheyePath, revision);
			}
		}
		for (String fisheyePath : revisionMap.keySet()) {
			aggregateSet.add(revisionMap.get(fisheyePath));
		}
		return aggregateSet;
	}

	private String getCurrentDate() {
		DateTime today = new DateTime();
		return "" + today.getYear() + "-" + today.getMonthOfYear() + "-" + today.getDayOfMonth();
	}

	private boolean repositoryExists(String repositoryName) {
		return FISHEYE_REPOS.contains(repositoryName);
	}
}
