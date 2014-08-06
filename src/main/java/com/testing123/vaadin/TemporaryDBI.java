package com.testing123.vaadin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.testing123.controller.AvailableResources;
import com.testing123.controller.SQLConnector;
import com.testing123.dataObjects.ConvertProject;

public class TemporaryDBI implements DatabaseInterface {

	@Override
	public Map<String, Integer> getMapToID(int projectID) {
		SQLConnector connector = new SQLConnector();
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
		return AvailableResources.getAvailableProjects();
	}

}
