package com.testing123.vaadin;

import java.sql.ResultSet;
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
		if (authors.size() == 0 || projects.size() == 0) {
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
		if (authors.size() == 0 || projects.size() == 0) {
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
		if (authors.size() == 0 || projects.size() == 0) {
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
			results = SQLConnector.basicQuery("SELECT * FROM " + date.getDBFormat() + " WHERE QUALIFIER = 'CLA';");
		} catch (Exception e) {
			return new ArrayList<WebData>();
		}
		// params: process(ResultSet r, metric (0), metric (1))
		return SQLConnector.process(results, "ncloc", "complexity");
	}
	
	private static String projectSet(Set<ConvertProject> projects) {
		List<ConvertProject> projectsList = new ArrayList<ConvertProject>(projects);
		String set = " (" + projectsList.get(0).getKey();
		for (int i = 1; i < projectsList.size(); i++) {
			set = set + "," + projectsList.get(i).getKey();
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
