package com.testing123.dataObjects;

public class ProjectListData {
	
	@Override
	public String toString() {
		return "ProjectListData [projectID=" + projectID + ",\t projectKey=" + projectKey + ",\t path=" + path + "]";
	}
	private int projectID;
	private String projectKey;
	private String path;
	
	public ProjectListData(int projectID, String projectKey, String path) {
		super();
		this.projectID = projectID;
		this.projectKey = projectKey;
		this.path = path;
	}
	
	public int getProjectID() {
		return projectID;
	}
	public String getProjectKey() {
		return projectKey;
	}
	public String getPath() {
		return path;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	public void setProjectKey(String projectKey) {
		this.projectKey = projectKey;
	}
	public void setPath(String path) {
		this.path = path;
	}	
}
