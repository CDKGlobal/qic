package com.testing123.vaadin;

import java.util.List;
import java.util.Map;

import com.testing123.dataObjects.ConvertProject;

public interface DatabaseInterface {
	
	public List<ConvertProject> getAvailableProjects();
	
	public Map<String, Integer> getMapToID(int projectID);

}
