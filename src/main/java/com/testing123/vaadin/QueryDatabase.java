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
import com.testing123.controller.UIState.Axis;
import com.testing123.controller.UIState.YAxis;

public class QueryDatabase {
	
//	public Set<DataPoint> getNCLOC(ConvertDate date, Set<ConvertProject> projects) {
//		Set<DataPoint> results = new HashSet<DataPoint>();
//		if (projects == null || date == null || projects.size() == 0) {
//			return results;
//		}
//		List<QueryData> dataList = getDataList(date, projects);
//		if (dataList.size() == 0) {
//			return results;
//		}
//		for (WebData file : dataList) {
//			DataPoint point = new DataPoint(file.getKey(), file.getMsr().get(0).getVal(), file.getMsr().get(1).getVal());
//			if (!dataList.contains(point)) {
//				results.add(point);
//			} else {
//				throw new IllegalStateException("Unhandeled duplicate case in: ncloc");
//			}
//		}
//		return results;
//	}
	
//	public Set<QueryData> getDateRangeMetrics(Axis axis, ConvertDate date, Set<ConvertProject> projects) {
//		if (projects == null || date == null || projects.size() == 0) {
//			return new HashSet<QueryData>();
//		}
//		Set<QueryData> results = getDataList(date, projects);
//		if (results == null) {
//			return results;
//		}
//		return results;
//	}
	
//	public Map<String, Double> getComplexity(ConvertDate date, Set<String> authors, Set<ConvertProject> projects) {
//		if (projects.size() == 0) {
//			return new HashMap<String, Double>();
//		}
//		Map<String, Double> map = new HashMap<String, Double>();
//		List<QueryData> dataList = getDataList(date, projects);
//		if (dataList == null) {
//			return map;
//		}
////		for (WebData file : dataList) {
////			if (!dataList.isEmpty()) {
////				map.put(formatKey(file.getKey()), file.getMsr().get(1).getVal());	//retrieves complexity
////			} else {
////				throw new IllegalStateException("Unhandeled duplicate case in: complexity");
////			}
////		}	
////		return map;
//	}
	
//	public Map<String, Double> getDeltaComplexity(ConvertDate startDate, ConvertDate endDate, Set<String> authors, Set<ConvertProject> projects) {
//		if (projects.size() == 0) {
//			return new HashMap<String, Double>();
//		}
//		Map<String, Double> initialComplexity = getComplexity(startDate, authors, projects);
//		Map<String, Double> finalComplexity = getComplexity(endDate, authors, projects);
//		Map<String, Double> deltaComplexity = new HashMap<String, Double>();
//		for (String name : finalComplexity.keySet()) {
//			if (initialComplexity.containsKey(name)) {
//				double change = finalComplexity.get(name) - initialComplexity.get(name);
//				if (change != 0) {
//					deltaComplexity.put(name, change);
//				}
//			} else {
//				deltaComplexity.put(name, finalComplexity.get(name));
//			}
//		}
//		return deltaComplexity;
//	}
	
	public Set<QueryData> getDataSet(ConvertDate startDate, ConvertDate endDate, Set<ConvertProject> projects) {
		SQLConnector conn = new SQLConnector();
		ResultSet results = null;
		try {
			results = conn.basicQuery("SELECT allFileHistory3.file_key, allFileList.name, ncloc, complexity, SUM(delta_complexity) AS delta_complexity FROM allFileHistory3 JOIN allFileList ON allFileList.file_id = allFileHistory3.file_id WHERE qualifier = 'CLA' AND allFileList.project_id IN " + projectIDSet(projects) + " AND dbdate < '" + endDate.getDBFormat() + "' AND dbdate > '" + startDate.getDBFormat() + "' GROUP BY file_key;");
			if (results == null || !results.isBeforeFirst()) {
//				results = new SQLConnector().basicQuery("SELECT DISTINCT project_id, allFileList.file_id, allFileList.file_key, name, scope, qualifier, date," + 
//						" ncloc, complexity FROM allFileList JOIN allFileHistory ON allFileList.file_key = allFileHistory.file_key WHERE project_id IN" 
//						+ projectIDSet(projects) +  " AND qualifier = 'CLA';");
			} 
			if (results == null || !results.isBeforeFirst()) {
				return new HashSet<QueryData>();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new HashSet<QueryData>();
		}
		return processDBResults(results);
	}
	
	private Set<QueryData> processDBResults(ResultSet rs) {
		Set<QueryData> processed = new HashSet<QueryData>();
		try {
			int counts = 0;
			while (rs.next()) {
				counts++;
				QueryData data = populateQueryData(rs);
				if (data != null) {
					processed.add(data);
				}
			}
			System.out.println(counts + " processed");
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return processed;
	}

	private QueryData populateQueryData(ResultSet rs) throws SQLException {
		QueryData data = new QueryData();
		try {
			data.setKey(rs.getString("file_key"));
			data.setName(rs.getString("name"));
			data.setNcloc(rs.getDouble("ncloc"));
//			data.setChurn(rs.getDouble("churn"));
			data.setComplexity(rs.getDouble("complexity"));
			data.setDeltaComplexity(rs.getDouble("delta_complexity"));
//			data.setIssues(rs.getDouble("issues"));
//			data.setDeltaIssues(rs.getDouble("deltaIssues"));
//			data.setCoverage(rs.getDouble("coverage"));
//			data.setDeltaCoverage(rs.getDouble("deltaCoverage"));
		} catch (Exception e) {
			return null;
		}
		return data;
	}
		
	private String projectIDSet(Set<ConvertProject> projects) {
		List<ConvertProject> projectsList = new ArrayList<ConvertProject>(projects);
		if (projects.size() == 0) {
			return "()";
		}
		String set = "(" + projectsList.get(0).getID();
		for (int i = 1; i < projectsList.size(); i++) {
			set = set + "," + projectsList.get(i).getID();
		}
		set = set + ")";
		return set;
	}
}
