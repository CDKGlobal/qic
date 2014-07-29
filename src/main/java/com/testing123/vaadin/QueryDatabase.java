package com.testing123.vaadin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.testing123.controller.AvailableResources;
import com.testing123.controller.SQLConnector;

public class QueryDatabase {
	
	public Map<String, Double> getNCLOC(ConvertDate date, Set<String> authors, Set<ConvertProject> projects) {
		if (projects.size() == 0) {
			return new HashMap<String, Double>();
		}
		Map<String, Double> map = new HashMap<String, Double>();
		List<WebData> dataList = getDataList(date, projects);
		if (dataList == null) {
			return map;
		}
		for (WebData file : dataList) {
			if (!map.containsKey(file.getName())) {
				map.put(formatKey(file.getKey()), file.getMsr().get(0).getVal());	//retrieves ncloc
			} else {
				throw new IllegalStateException("Unhandeled duplicate case in: ncloc");
			}
		}
		return map;
	}
	
	public Map<String, Double> getComplexity(ConvertDate date, Set<String> authors, Set<ConvertProject> projects) {
		if (projects.size() == 0) {
			return new HashMap<String, Double>();
		}
		Map<String, Double> map = new HashMap<String, Double>();
		List<WebData> dataList = getDataList(date, projects);
		if (dataList == null) {
			return map;
		}
		for (WebData file : dataList) {
			if (!dataList.isEmpty()) {
				map.put(formatKey(file.getKey()), file.getMsr().get(1).getVal());	//retrieves complexity
			} else {
				throw new IllegalStateException("Unhandeled duplicate case in: complexity");
			}
		}	
		return map;
	}
	
	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate, Set<String> authors, Set<ConvertProject> projects) {
		if (projects.size() == 0) {
			return new HashMap<String, Double>();
		}
		Map<String, Double> initialComplexity = getComplexity(startDate, authors, projects);
		Map<String, Double> finalComplexity = getComplexity(endDate, authors, projects);
		Map<String, Double> deltaComplexity = new HashMap<String, Double>();
		for (String name : finalComplexity.keySet()) {
			if (initialComplexity.containsKey(name)) {
				double change = finalComplexity.get(name) - initialComplexity.get(name);
				if (change != 0) {
					deltaComplexity.put(name, change);
				}
			} else {
				deltaComplexity.put(name, finalComplexity.get(name));
			}
		}
		return deltaComplexity;
	}
	
	private static List<WebData> getDataList(ConvertDate date, Set<ConvertProject> projects) {
		ResultSet results = null;
		try {
			results = SQLConnector.basicQuery("SELECT project_id, allFileList.file_id, allFileList.file_key, name, scope, qualifier, date," + 
				" ncloc, complexity FROM allFileList JOIN allFileHistory ON allFileList.file_key = allFileHistory.file_key WHERE project_id IN" 
				+ projectSet(projects) +  " AND allFileHistory.date LIKE '" + date.getDBQueryFormat() + "' AND qualifier = 'CLA';");
			if (!results.isBeforeFirst()) {    
				results = SQLConnector.basicQuery("SELECT project_id, allFileList.file_id, allFileList.file_key, name, scope, qualifier, date," + 
						" ncloc, complexity FROM allFileList JOIN allFileHistory ON allFileList.file_key = allFileHistory.file_key WHERE project_id IN" 
						+ projectSet(projects) +  " AND qualifier = 'CLA';");
				} 
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<WebData>();
		}
		return processTwo(results, "ncloc", "complexity");
	}
	
	private static List<WebData> processTwo(ResultSet rs, String... metrics) {
		List<WebData> processed = new ArrayList<WebData>();
		try {
			while (rs.next()) {
				WebData data = new WebData();
				data.setProjectId(rs.getInt("project_id"));
				data.setId(rs.getInt("file_id"));
				data.setKey(rs.getString("file_key"));
				data.setName(rs.getString("name"));
				data.setScope(rs.getString("scope"));
				data.setQualifier(rs.getString("qualifier"));
				data.setDate(rs.getString("date"));

				List<Msr> msrList = new ArrayList<Msr>();

				String[] msrKeys = metrics;
				for (String msrKey : msrKeys) {
					Msr msr = new Msr();
					msr.setKey(msrKey);
					msr.setVal(rs.getDouble(msrKey));
					msrList.add(msr);
				}
				data.setMsr(msrList);
				processed.add(data);
			}
			rs.close();
			return processed;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	private static String projectSet(Set<ConvertProject> projects) {
		List<ConvertProject> projectsList = new ArrayList<ConvertProject>(projects);
		String set = "(" + projectsList.get(0).getID();
		for (int i = 1; i < projectsList.size(); i++) {
			set = set + "," + projectsList.get(i).getID();
		}
		set = set + ")";
		return set;
	}
	
	private static String formatKey(String longKey) {
		String[] pack = longKey.split(":");
		String replacement = pack[1];
		return longKey.replaceAll("com.cobalt.*:", replacement + ".");
	}
}
