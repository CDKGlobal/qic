package com.testing123.vaadin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.testing123.controller.AvailableResources;
import com.testing123.controller.SQLConnector;
import com.testing123.dataObjects.ConvertDate;
import com.testing123.dataObjects.ConvertProject;
import com.testing123.dataObjects.RepoAndDirData;
import com.testing123.interfaces.DatabaseInterface;
import com.testing123.ui.Preferences;

public class UseSQLDatabase implements DatabaseInterface{
	
	private String databaseName;
	
	public UseSQLDatabase(){
		this(Preferences.DB_NAME);
	}
	
	public UseSQLDatabase(String name){
		databaseName = name;
	}
		
	@Override
	public Map<String, Integer> getMapToID(int projectID) {
		SQLConnector connector = new SQLConnector(databaseName);
		ResultSet results = connector.basicQuery("SELECT file_id,file_key,scope,qualifier FROM allFileList WHERE project_id=" + projectID + ";");
		Map<String, Integer> mapNameToID = new HashMap<String, Integer>();
		if (results != null) {
			try {
				while (results.next()) {
					int fileID = results.getInt("file_id");
					String fileKey = results.getString("file_key");
					String scope = results.getString("scope");
					String qualifier = results.getString("qualifier");
					if("FIL".equals(scope) && "CLA".equals(qualifier)){
						String formattedName = nameFormat(fileKey);
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

	@Override
	public List<ConvertProject> getAvailableProjects() {
		SQLConnector conn = new SQLConnector(databaseName);
		List<ConvertProject> list = AvailableResources.getAvailableProjects(conn);
		conn.close();
		return list;
	}

	@Override
	public List<String> getAvailableAuthors() {
		SQLConnector conn = new SQLConnector(databaseName);
		List<String> list = AvailableResources.getAvailableAuthors(conn);
		conn.close();
		return list;
	}

	@Override
	public List<ConvertDate> getAvailableDates() {
		SQLConnector conn = new SQLConnector(databaseName);
		List<ConvertDate> list = AvailableResources.getAvailableDates(conn);
		conn.close();
		return list;
	}

	@Override
	public int getProjectID(String fileKey) {
		SQLConnector connector = new SQLConnector(databaseName);
		int projectID = -1;
		ResultSet results = connector.basicQuery("SELECT project_id FROM allFileList WHERE file_key='" + fileKey + "';");
		try {
			if (results != null && results.next()) {
				projectID = results.getInt("project_id");
				if(results.next()){
					System.out.println("found two");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connector.close();
		return projectID;
	}
	
	@Override
	public RepoAndDirData getRepoAndDirFromFileKey(String fileKey){
		int projectID = getProjectID(fileKey);
		String projectPath = getProjectPath(projectID);
		return new RepoAndDirData(projectPath);
	}
	
	@Override
	public String getProjectPath(int projectID) {
		SQLConnector connector = new SQLConnector(databaseName);
		ResultSet results = connector.basicQuery("SELECT path FROM projectList WHERE project_id=" + projectID + ";");
		String projectPath = null;
		try {
			if (results != null && results.next()) {
				projectPath = results.getString("path");
				if(results.next()){
					System.out.println("found two");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connector.close();
		return projectPath;
	}

}
