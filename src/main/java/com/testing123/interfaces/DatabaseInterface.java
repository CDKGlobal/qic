package com.testing123.interfaces;

import java.util.List;
import java.util.Map;

import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.RepoAndDirData;

public interface DatabaseInterface {
	
	public List<ConvertProject> getAvailableProjects();
	
	public Map<String, Integer> getMapToID(int projectID);
	
	public List<String> getAvailableAuthors();
	
	public List<ConvertDate> getAvailableDates();
	
	public int getProjectID(String fileKey);
	
	public String getProjectPath(int projectID);
	
	public RepoAndDirData getRepoAndDirFromFileKey(String fileKey);
}
